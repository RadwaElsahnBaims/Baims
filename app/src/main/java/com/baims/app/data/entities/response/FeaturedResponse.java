package com.baims.app.data.entities.response;

import com.baims.app.data.entities.Section;

import java.util.List;

import kotlin.OptionalExpectation;

/**
 * Created by Radwa Elsahn on 1/23/2019.
 */
public class FeaturedResponse extends BaseResponse {


    private int login;
    private List<Section> data;

    public int getLogin() {
        return login;
    }

    public void setLogin(int login) {
        this.login = login;
    }

    public List<Section> getData() {
        return data;
    }

    public void setData(List<Section> data) {
        this.data = data;
    }
}
