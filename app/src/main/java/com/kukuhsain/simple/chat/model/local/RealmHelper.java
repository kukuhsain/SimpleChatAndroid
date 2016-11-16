package com.kukuhsain.simple.chat.model.local;

import com.kukuhsain.simple.chat.model.pojo.Chat;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by kukuh on 04/11/16.
 */

public class RealmHelper {
    private static RealmHelper INSTANCE;

    private RealmHelper() {}

    public static RealmHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RealmHelper();
        }
        return INSTANCE;
    }

    public void addChat(Chat chat) {
        List<Chat> chats = getAllChats();
        boolean isExisted = false;
        for (Chat chat1 : chats) {
            if (chat.getSampleId() == chat1.getSampleId()) {
                isExisted = true;
            }
        }
        if (!isExisted) {
            Realm.getDefaultInstance().executeTransaction(realm -> {
                realm.copyToRealm(chat);
            });
        }
    }

    public void addChats(List<Chat> chats) {
        List<Chat> realmChats = getAllChats();
        for (Chat chat : chats) {
            boolean isExisted = false;
            for (Chat realmChat : realmChats) {
                if (realmChat.getSampleId() == chat.getSampleId()) {
                    isExisted = true;
                }
            }
            if (!isExisted) {
                Realm.getDefaultInstance().executeTransaction(realm -> {
                    realm.copyToRealm(chat);
                });
            }
        }
    }

    public List<Chat> getAllChats() {
        RealmResults<Chat> iterableChats = Realm.getDefaultInstance()
                .where(Chat.class).findAll();
        return Realm.getDefaultInstance().copyFromRealm(iterableChats);
    }
}
