package io.rapidpro.sdk.listeners;

import io.rapidpro.sdk.core.models.Contact;

/**
 * Created by john-mac on 6/28/16.
 */
public interface ContactListener extends ErrorListener {

    void onContactSaved(Contact contact);

}
