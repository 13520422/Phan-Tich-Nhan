/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timkiemfb;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.Page;
import com.restfb.types.Post;
import com.restfb.types.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 *
 * @author phanlam
 */
public class TimKiem {
    private String accessToken;
    private FacebookClient fbClient;
    private String tuKhoa;
    private Connection<User> danhSachUser;
    private Connection<Page> danhSachPage;
    private List<Post> danhsachPost;
    
    //property
    
    public void setDanhsachPost(List<Post> danhsachPost) {
        this.danhsachPost = danhsachPost;
    }

    public List<Post> getDanhsachPost() {
        return danhsachPost;
    }
    
    public String getTuKhoa() {
        return tuKhoa;
    }

    public Connection<User> getDanhSachUser() {
        return danhSachUser;
    }

    public Connection<Page> getDanhSachPage() {
        return danhSachPage;
    }

    public void setTuKhoa(String tuKhoa) {
        this.tuKhoa = tuKhoa;
    }

    public void setDanhSachUser(Connection<User> danhSachUser) {
        this.danhSachUser = danhSachUser;
    }

    public void setDanhSachPage(Connection<Page> danhSachPage) {
        this.danhSachPage = danhSachPage;
    }
    
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setFbClient(FacebookClient fbClient) {
        this.fbClient = fbClient;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public FacebookClient getFbClient() {
        return fbClient;
    }
    
    //contructor
    public TimKiem()
    {
        this.accessToken="";
        this.tuKhoa="";
        this.fbClient=null;
        
    }
    public TimKiem(String accessToken)
    {
        this.accessToken=accessToken;
        this.tuKhoa="";
        fbClient = new DefaultFacebookClient(this.accessToken, Version.UNVERSIONED);
        this.danhsachPost= new ArrayList<Post>();
       
    }
    
    public TimKiem(String accessToken,String tuKhoa)
    {
        this.accessToken=accessToken;
        this.tuKhoa=tuKhoa;
        fbClient = new DefaultFacebookClient(this.accessToken, Version.UNVERSIONED);
        this.danhsachPost= new ArrayList<Post>();
        
        
       
//        AccessToken extendedAccessToken = fbClient.obtainExtendedAccessToken("510355519354929 ", "5b596d6a2e0833095c5045ade1b7eb55", accessToken);
//        this.accessToken = extendedAccessToken.getAccessToken();
//        System.out.println("acc: " + this.accessToken);
    }
    
    public void TruyXuatDLFB()
    {
        try
        {
//            Connection<User> publicUserSearch = this.fbClient.fetchConnection("search", User.class,
//            Parameter.with("q", this.tuKhoa),Parameter.with("type", "user"),Parameter.with("limit",1000 ));
//            if(publicUserSearch!=null)
//                this.danhSachUser=publicUserSearch;
            
            Connection<Page> publicPageSearch = this.fbClient.fetchConnection("search", Page.class,
            Parameter.with("q", this.tuKhoa),Parameter.with("type", "page"),Parameter.with("limit",1 ));
            if(publicPageSearch!=null)
            {
                this.danhSachPage=publicPageSearch;
                LayBaiDang(publicPageSearch);
//                while(publicPageSearch.hasNext())
//                {
//                     publicPageSearch = this.fbClient.fetchConnectionPage(publicPageSearch.getNextPageUrl(), Page.class);  
//                    LayBaiDang(publicPageSearch);
//                    
//                }
            }
           
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void LayBaiDang(Connection<Page> publicPageSearch)
    {
        for (int i=0;i<publicPageSearch.getData().size();i++)
        {
            Connection<Post> listpost = this.fbClient.fetchConnection(publicPageSearch.getData().get(i).getId()+"/feed", Post.class);
                                                          
            
            if(listpost!=null)
            {
                for (int j=0;j<listpost.getData().size();j++)
                {
                    try
                    {
                        Post p = fbClient.fetchObject(listpost.getData().get(j).getId() ,Post.class,
                             Parameter.with("fields", "from,to,likes.limit(0).summary(true),comments.limit(0).summary(true),shares.limit(0).summary(true),name,message,link,picture"));

                        this.danhsachPost.add(p);
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                            
                }
            }
//            while(listpost.hasNext())
//            {
//                 listpost = this.fbClient.fetchConnectionPage(listpost.getNextPageUrl(), Post.class);  
//                for (int j=0;j<listpost.getData().size();j++)
//                {
//                    try
//                    {
//                        Post p = this.fbClient.fetchObject(listpost.getData().get(j).getId() ,Post.class,
//                             Parameter.with("fields", "from,to,likes.limit(0).summary(true),comments.limit(0).summary(true),shares.limit(0).summary(true),name,message,link,picture"));
//                        this.danhsachPost.add(p);
//                    }
//                    catch(Exception e)
//                    {
//                        e.printStackTrace();
//                    }
//                            
//                }
//                     
//            }
        }
    }
    
}
