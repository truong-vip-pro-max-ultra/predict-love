package com.truongjae.predictlove.service.impl;

import com.truongjae.predictlove.dto.Profile;
import com.truongjae.predictlove.dto.gson_mapping.avatar.Avatar;
import com.truongjae.predictlove.dto.gson_mapping.gender.Gender;
import com.truongjae.predictlove.dto.gson_mapping.top_friend.Feedback;
import com.truongjae.predictlove.dto.gson_mapping.top_friend.Friend;
import com.truongjae.predictlove.dto.gson_mapping.top_friend.TopFriend;
import com.truongjae.predictlove.dto.resp.FriendResponse;
import com.truongjae.predictlove.entity.Cookies;
import com.truongjae.predictlove.entity.PriceService;
import com.truongjae.predictlove.entity.User;
import com.truongjae.predictlove.enumtype.EntityType;
import com.truongjae.predictlove.enumtype.RequestMethod;
import com.truongjae.predictlove.exception.BadRequestException;
import com.truongjae.predictlove.repoitory.CookiesRepository;
import com.truongjae.predictlove.repoitory.PriceServiceRepository;
import com.truongjae.predictlove.repoitory.UserRepository;
import com.truongjae.predictlove.service.FBApiService;
import com.truongjae.predictlove.service.UIDService;
import com.truongjae.predictlove.utils.ApiUtil;
import com.truongjae.predictlove.utils.CallApi;
import com.truongjae.predictlove.utils.CutString;
import com.truongjae.predictlove.utils.MessageResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class FBApiServiceImpl implements FBApiService {
    private final UIDService uidService;
    private final CookiesRepository cookiesRepository;

    private final Profile profile;
    private final UserRepository userRepository;

    private final PriceServiceRepository priceServiceRepository;
    @Override
    public FriendResponse getPredictLove(String urlFB) {
        User user = userRepository.findOneByUsername(profile.getUsername());

        List<PriceService> priceServiceList = priceServiceRepository.findAll();
        PriceService priceService = priceServiceList.get(0);

        if(user.getAccountBalance()>=priceService.getPrice()){

            user.setAccountBalance(user.getAccountBalance()-priceService.getPrice()); /// payment
            userRepository.save(user);

            Random random = new Random();
            List<Cookies> cookiesList = cookiesRepository.findAll();
            String fb_dtsg = null;
            String cookie = null;
            Cookies cookiesChoice=null;
            for(int i = 0;i<5;i++){
                int randomIndex = random.nextInt(cookiesList.size());
                cookiesChoice = cookiesList.get(randomIndex);
                if(cookiesChoice.getStatus()==false){
                    updateStatusCookieChoice(cookiesChoice,true);
                    cookie = cookiesChoice.getCookie();
                    fb_dtsg = getFb_dtsg(cookie);
                    if(fb_dtsg != null)
                        break;
                }
                updateStatusCookieChoice(cookiesChoice,false);
            }

            if(fb_dtsg != null){
                String userId = null;
                try {
                    userId = uidService.getUidFromUrl(EntityType.USER,urlFB,cookie);
                }
                catch (Exception e){
                }

                if(userId!=null){
                    String accessToken = null;
                    try {
                        accessToken = getAccessTokenNoFullAccess(cookie);
                    }
                    catch (Exception e){
                        updateStatusCookieChoice(cookiesChoice,false);
                        throw new BadRequestException(MessageResponse.NOT_FOUND_LOVE);
                    }

                    String myGender = getGender(userId,cookie,accessToken);
                    String myUrlAvatar = getAvatar(userId,cookie,accessToken);
                    String myFullName = uidService.getFullNameFromUrl("https://www.facebook.com/"+userId,cookie);
                    List<Friend> topFriends = getTopFriend(userId,fb_dtsg,cookie);
                    if(topFriends != null){
                        for(Friend friend : topFriends){
                            String friendGender = getGender(friend.getId(),cookie,accessToken);
                            if(friendGender!=null && !myGender.equals(friendGender)){
                                String friendUrlAvatar = getAvatar(friend.getId(),cookie,accessToken);
                                FriendResponse friendResponse = FriendResponse.builder()
                                        .myId(userId)
                                        .myFullName(myFullName)
                                        .myUrlAvatar(myUrlAvatar)
                                        .friendId(friend.getId())
                                        .friendFullName(friend.getName())
                                        .friendUrlAvatar(friendUrlAvatar)
                                        .build();
                                updateStatusCookieChoice(cookiesChoice,false);
                                return friendResponse;
                            }
                        }
                    }
                }
                else{
                    throw new BadRequestException(MessageResponse.URL_FB_UNDEFINED);
                }
            }
            if(cookiesChoice!=null)
                updateStatusCookieChoice(cookiesChoice,false);
            throw new BadRequestException(MessageResponse.NOT_FOUND_LOVE);
        }
        else{
            throw new BadRequestException(MessageResponse.ACCOUNT_IS_NOT_ENOUGH);
        }
    }


    @Override
    public String getFb_dtsg(String cookie) {
        try {
            String url = "https://www.facebook.com";
            String resp = CallApi.getResponse(RequestMethod.GET,url,cookie,null,false);
            String fb_dtsg = CutString.cut(resp,"[\"DTSGInitialData\",[],{\"token\":\"",true);
            fb_dtsg = CutString.cut(fb_dtsg,"\"",false);
            return fb_dtsg.length() > 15 ? fb_dtsg : null;
        }
        catch (Exception e){
            return null;
        }
    }

    @Override
    public String getAccessTokenNoFullAccess(String cookie) {
        String url = getUrlCamp(cookie);
        if(url!=null){
            String resp = CallApi.getResponse(RequestMethod.GET,url,cookie,null,false);
            resp = CutString.cut(resp,"__accessToken=\"",true);
            resp = CutString.cut(resp,"\"",false);
            return resp;
        }
        return null;
    }
    public void updateStatusCookieChoice(Cookies cookies, Boolean status){
        cookies.setStatus(status);
        cookiesRepository.save(cookies);
    }

    public String getGender(String userId,String cookie, String accessToken) {
        try {
            String url = "https://graph.facebook.com/"+userId+"?fields=gender&access_token="+accessToken;
            Gender gender = ApiUtil.getMethod(url,null,Gender.class,cookie);
            return gender.getGender();
        }
        catch (Exception e){
            return null;
        }
    }
    public String getAvatar(String userId,String cookie, String accessToken) {
        try {
            String url = "https://graph.facebook.com/"+userId+"?fields=picture.type(large)&access_token="+accessToken;
            Avatar avatar = ApiUtil.getMethod(url,null,Avatar.class,cookie);
            return avatar.getPicture().getData().getUrl();
        }
        catch (Exception e){
            return null;
        }
    }

    public String getUrlCamp(String cookie){
        try {
            String url = "https://www.facebook.com/adsmanager/manage/campaigns";
            String resp = CallApi.getResponse(RequestMethod.GET,url,cookie,null,false);
            resp = CutString.cut(resp,"window.location.replace(\"",true);
            resp = CutString.cut(resp,"\"",false);
            return resp.replace("\\/","/");
        }
        catch (Exception e){
            return null;
        }
    }
    public List<Friend> getTopFriend(String userId,String fb_dtsg,String cookie){ // String userId,String fbdtsg,String cookie
        try {
            String url = "https://www.facebook.com/api/graphql/";
            String body = "fb_dtsg=" + fb_dtsg + "&q=node("+userId+"){timeline_feed_units.first(100).after(){page_info,edges{node{id,creation_time,feedback{reactors{nodes{id,name}},commenters{nodes{id,name}}}}}}}";
            Map<String, Friend> info = new HashMap<>();
            TopFriend topFriend = ApiUtil.postMethod(url, body, TopFriend.class, cookie, true);
            topFriend.getTimeline_feed_units().getEdges().stream().forEach(
                    edges -> {
                        Feedback feedback = edges.getNode().getFeedback();
                        if(feedback!=null){
                            feedback.getReactors().getNodes().stream().forEach(
                                    reactorsNodes -> {
                                        String id = reactorsNodes.getId();
                                        String name = reactorsNodes.getName();
                                        if (!info.containsKey(id)) {
                                            Friend friend = new Friend();
                                            friend.setId(id);
                                            friend.setName(name);
                                            friend.setTimeline(1L);
                                            info.put(id, friend);
                                        } else {
                                            Friend friend = info.get(id);
                                            friend.setTimeline(friend.getTimeline() + 1);
                                            info.put(id, friend);
                                        }
                                    }
                            );
                            feedback.getCommenters().getNodes().stream().forEach(
                                    commentersNodes -> {
                                        String id = commentersNodes.getId();
                                        String name = commentersNodes.getName();
                                        if (!info.containsKey(id)) {
                                            Friend friend = new Friend();
                                            friend.setId(id);
                                            friend.setName(name);
                                            friend.setTimeline(1L);
                                            info.put(id, friend);
                                        } else {
                                            Friend friend = info.get(id);
                                            friend.setTimeline(friend.getTimeline() + 1);
                                            info.put(id, friend);
                                        }
                                    }
                            );
                        }
                    }
            );

            final Set<Map.Entry<String, Friend>> entry = info.entrySet();
            final Comparator<Map.Entry<String, Friend>> comparator = new Comparator<Map.Entry<String, Friend>>() {
                @Override
                public int compare(Map.Entry<String, Friend> o1, Map.Entry<String, Friend> o2) {
                    Long value1 = o1.getValue().getTimeline();
                    Long value2 = o2.getValue().getTimeline();
                    return value2.compareTo(value1);
                }
            };
            final SortedSet<Map.Entry<String, Friend>> sortedSet = new TreeSet(comparator);
            sortedSet.addAll(entry);
            final Map<String, Friend> sortedMap = new LinkedHashMap<String, Friend>();
            for (Map.Entry<String, Friend> entry1 : sortedSet) {
                sortedMap.put(entry1.getKey(), entry1.getValue());
            }
            List<Friend> friends = new ArrayList<>();
            for (String key : sortedMap.keySet()) {
                friends.add(sortedMap.get(key));
            }
            return friends;
        }
        catch (Exception e){
            return null;
        }
    }


    public static void main(String[] args) {
//        FBApiServiceImpl fbApiService = new FBApiServiceImpl();
//        fbApiService.getTopFriend().stream().forEach(System.out::println);
    }
}
