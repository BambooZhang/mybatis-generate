package com.xialeme.mybatis_generate;

import java.io.IOException;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class Test {

	
	
	
	
	public static void main(String[] args) {

		/*OkHttpClient client = new OkHttpClient();
		
		
		 // 表单提交 这种能满足大部分的需求  
        RequestBody body =  RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "{\"data\":\"121\",\"data1\":\"2232\"}");
		
		Request request = new Request.Builder()
        .url("http://localhost:8080/xm-web-sys/jzmApp/callBackJzmTask")
        .post(body).build();
		
		
		try {
			Response response = client.newCall(request).execute();
			if(response.isSuccessful()){
			    System.out.println(response.code());
			    System.out.println(response.body().string());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
		
		
		
		
		//
		System.out.println(Integer.toBinaryString(129) );
		System.out.println(1<<4 );
		
		
	}
	
	
}
