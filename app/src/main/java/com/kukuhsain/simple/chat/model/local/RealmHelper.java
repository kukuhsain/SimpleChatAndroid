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

    public void addSample(Chat destination) {
        List<Chat> destinations = getAllSamples();
        boolean isExisted = false;
        for (Chat destination1 : destinations) {
            if (destination.getSampleId() == destination1.getSampleId()) {
                isExisted = true;
            }
        }
        if (!isExisted) {
            Realm.getDefaultInstance().executeTransaction(realm -> {
                realm.copyToRealm(destination);
            });
        }
    }

    public void addSamples(List<Chat> destinations) {
        List<Chat> realmChats = getAllSamples();
        for (Chat destination : destinations) {
            boolean isExisted = false;
            for (Chat realmChat : realmChats) {
                if (realmChat.getSampleId() == destination.getSampleId()) {
                    isExisted = true;
                }
            }
            if (!isExisted) {
                Realm.getDefaultInstance().executeTransaction(realm -> {
                    realm.copyToRealm(destination);
                });
            }
        }
    }

    public List<Chat> getAllSamples() {
        RealmResults<Chat> iterableChats = Realm.getDefaultInstance()
                .where(Chat.class).findAll();
        return Realm.getDefaultInstance().copyFromRealm(iterableChats);
    }
}
