/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.AmritaResouce;

/**
 * {@link Word} represents a vocabulary word that the user wants to learn.
 * It contains a default translation and a Miwok translation for that word.
 */
public class Word {

    /** Default translation for the word */
    private String mSubNotes;

    /** Miwok translation for the word */
    private String mSubject;

    private String mURL;

    /**
     * Create a new Word object.
     *
     * @param subNotes is the word in a language that the user is already familiar with
     *                           (such as English)
     * @param subjectName is the word in the Miwok language
     */
    public Word( String subjectName,String subNotes, String URL) {
        mSubNotes = subNotes;
        mSubject = subjectName;
        mURL=URL;
    }

    /**
     * Get the default translation of the word.
     */
    public String getSubNotesName() {
        return mSubNotes;
    }

    /**
     * Get the Miwok translation of the word.
     */
    public String getSubjectName() {
        return mSubject;
    }

    public String getURL(){ return mURL; }
}