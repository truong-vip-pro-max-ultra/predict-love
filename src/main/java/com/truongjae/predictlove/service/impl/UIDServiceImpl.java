package com.truongjae.predictlove.service.impl;

import com.truongjae.predictlove.enumtype.EntityType;
import com.truongjae.predictlove.enumtype.RequestMethod;
import com.truongjae.predictlove.service.UIDService;
import com.truongjae.predictlove.utils.CallApi;
import com.truongjae.predictlove.utils.CutString;
import org.springframework.stereotype.Service;

@Service
public class UIDServiceImpl implements UIDService {
    @Override
    public String getUIDFromCookie(String cookie){
        String uid = CutString.cut(cookie,"c_user=",true);
        uid = CutString.cut(uid,";",false);
        return uid;
    }
    public boolean isValidUid(String UID){
        try {
            Long.parseLong(UID);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    private String getUid(String resp,String keysStart[],String keysFinish[]){
        for(int i=0;i<keysStart.length;i++){
            String uid = CutString.cut(resp,keysStart[i],true);
            uid = CutString.cut(uid,keysFinish[i],false);
            if(isValidUid(uid))
                return uid;
        }
        return null;
    }

    @Override
    public String getPostIdFromUrl(String link, String cookie) {
        String listKey[] = {"\",\"feedback_id\":\"",
                "\",\"video_id\":\"",
                "\"params\":{\"story_fbid\":\"",
                "\"params\":{\"fbid\":\"",
                "\"story_token\":\"",
                "id=\"actions_"};
        for(String key : listKey){
            String resp = CallApi.getResponse(RequestMethod.GET,link,cookie,null,false);
            if(resp.indexOf(key)!=-1){
                try {
                    String postId = CutString.cut(resp,key,true);
                    postId = CutString.cut(postId,"\"",false);
                    return postId;
                }
                catch (Exception e){
                }
            }
        }
        return null;
    }

    @Override
    public String getUidFromUrl(EntityType entityType, String link, String cookie) {
        String resp = CallApi.getResponse(RequestMethod.GET,link,cookie,null,false);
        String keysStart[];
        String keysFinish[];

        switch (entityType){
            case USER:
                keysStart = new String[] {"\"userID\":\"", "profile_id="};
                keysFinish = new String[] {"\"", "&"};
                break;
            case PAGE:
                keysStart = new String[] {"\"props\":{\"pageID\":\"","\"userID\":\"","&amp;id="};
                keysFinish = new String[]  {"\"","\"","&"};
                break;
            case GROUP:
                keysStart = new String[] {"\"props\":{\"groupID\":\"","?media_id="};
                keysFinish = new String[] {"\"","\""};
                break;
            default:
                return null;
            }
        return keysStart!= null? getUid(resp,keysStart,keysFinish) : null;
    }

    @Override
    public String getFullNameFromUrl(String link, String cookie) {
        try {
            String resp = CallApi.getResponse(RequestMethod.GET,link,cookie,null,false);
            String fullName = CutString.cut(resp,"<title>",true);
            fullName = CutString.cut(fullName,"</title>",false);
            fullName = fullName.replace(" | Facebook","");
            return fullName;
        }
        catch (Exception e){
            return "Tên như lồn";
        }
    }
}
