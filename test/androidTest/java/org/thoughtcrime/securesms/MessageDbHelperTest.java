package org.thoughtcrime.securesms;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MessageDbHelperTest {

    @Inject
    Context mContext;

    MessageDbHelper messageDbHelper;
    ContentValues contentValues;
    SQLiteDatabase writableDatabase;
    SQLiteDatabase readableDatabase;
    @Before
    public void setUp() throws Exception {
        mContext = ApplicationProvider.getApplicationContext();
        messageDbHelper = new MessageDbHelper(mContext);
        contentValues = new ContentValues();
        writableDatabase = messageDbHelper.getWritableDatabase();
        readableDatabase = messageDbHelper.getReadableDatabase();

        // input dummy data
        contentValues.put(StarredMessageContract.MessageEntry.MESSAGE_ID_STAR,"100");
        contentValues.put(StarredMessageContract.MessageEntry.THREAD_ID_STAR, "200");
        contentValues.put(StarredMessageContract.MessageEntry.TYPE_STAR, "SMS");
        contentValues.put(StarredMessageContract.MessageEntry.CONTACT, "Dummy Address");
        contentValues.put(StarredMessageContract.MessageEntry.MESSAGE_BODY_STAR ,  "Hello World");
        contentValues.put(StarredMessageContract.MessageEntry.TIME_STAMP ,  "1");
        contentValues.put(StarredMessageContract.MessageEntry.DATE_RECEIVED ,  "1");
        contentValues.put(StarredMessageContract.MessageEntry.DATE_SENT ,  "1");
    }
    @Test
    public void addAndDeletePass() {

        messageDbHelper.addMessage(contentValues, writableDatabase);
        Cursor cursor = messageDbHelper.readMessage(readableDatabase,"200");

        String bodyFromCursor = "";
        if (cursor.moveToNext())
        {
            bodyFromCursor = cursor.getString(cursor.getColumnIndex(StarredMessageContract.MessageEntry.MESSAGE_BODY_STAR));

        }

        // message body should be "Hello World"
        assertEquals("Hello World",bodyFromCursor);

        messageDbHelper.deleteMessage(writableDatabase,"100","200");
        cursor = messageDbHelper.readMessage(readableDatabase,"200");

        // No record should be return after delete
        assertFalse(cursor.moveToNext());
    }

    @Test
    public void addAndDeleteMessageWrongMessageBody() {

        contentValues.put(StarredMessageContract.MessageEntry.MESSAGE_BODY_STAR ,  "Bye World");

        messageDbHelper.addMessage(contentValues, writableDatabase);
        Cursor cursor = messageDbHelper.readMessage(readableDatabase,"200");

        String bodyFromCursor = "";
        if (cursor.moveToNext())
        {
            bodyFromCursor = cursor.getString(cursor.getColumnIndex(StarredMessageContract.MessageEntry.MESSAGE_BODY_STAR));

        }

        // message body should be "Hello World"
        assertNotEquals("Hello World",bodyFromCursor);

        messageDbHelper.deleteMessage(writableDatabase,"100","200");
        cursor = messageDbHelper.readMessage(readableDatabase,"200");

        // No record should be return after delete
        assertFalse(cursor.moveToNext());
    }


    @After
    public void tearDown() {
        messageDbHelper.close();
    }
}