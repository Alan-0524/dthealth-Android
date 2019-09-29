package com.dthealth.util;

import android.content.Context;

import androidx.room.Room;

import com.dthealth.dao.RoomDb;

public class RoomDbClient {
    private static RoomDb roomDb;
    private static RoomDbClient instance;

    public static synchronized RoomDbClient getInstance() {
        if (instance == null) {
            instance = new RoomDbClient();
        }
        return instance;
    }

    public void initRoomDb(Context context) {
        if (roomDb == null) {
            roomDb = Room.databaseBuilder(context, RoomDb.class, "dt")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
    }

    public RoomDb getRoomDb() {
        return roomDb;
    }
}
