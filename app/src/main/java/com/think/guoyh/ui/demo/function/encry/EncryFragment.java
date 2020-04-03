package com.think.guoyh.ui.demo.function.encry;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.gyh.baselib.utils.mylog.Logger;
import com.think.guoyh.R;
import com.think.guoyh.base.MyBaseFragment;
import com.think.guoyh.widget.ShapeButton;

import java.security.KeyPair;

/**
 * A simple {@link Fragment} subclass.
 * 加密
 */
public class EncryFragment extends MyBaseFragment implements View.OnClickListener {


    private ShapeButton rsa;

    public EncryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_encry, container, false);
        initView(view);
        return view;
    }

    @Override
    protected void loadData() {

    }

    private void initView(View view) {
        rsa = (ShapeButton) view.findViewById(R.id.rsa);
        rsa.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rsa:
                toRsa();
                break;
        }
    }
    //        byte[] data = "llo RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSA Hello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSA Hello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSA Hello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSA Hello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSA Hello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSA Hello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSA Hello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSA Hello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSA Hello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSA Hello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSA Hello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSA Hello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSA Hello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSA Hello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSA Hello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSA Hello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSA Hello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSA Hello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSA Hello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSA Hello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSA Hello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSA Hello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSA Hello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSA Hello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSA Hello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSA Hello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSA Hello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSA Hello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSAHello RSA".getBytes();

    public void toRsa() {
        byte[] data = ("按时吃药按时吃药按时吃药按时吃药按时吃药按时吃药").getBytes();

        byte[] base64Data = Base64.encode(data, Base64.DEFAULT);

        Logger.dd("原始数据长度： " + base64Data.length);

        KeyPair keyPair = RSA.generateKeyPair(2048);

        byte[] publicKeyBase64Data = Base64.encode(keyPair.getPublic().getEncoded(), Base64.DEFAULT);
        byte[] privateKeyBase64Data = Base64.encode(keyPair.getPrivate().getEncoded(), Base64.DEFAULT);

        try {
            long start = System.currentTimeMillis();
            byte[] encryptData = RSA.encryptWithPublicKeyBlock(base64Data, Base64.decode(publicKeyBase64Data, Base64.DEFAULT));
            Logger.dd("公钥加密耗时: " + (System.currentTimeMillis() - start));
            Logger.dd("公钥加密后长度: " + encryptData.length);

            start = System.currentTimeMillis();
            byte[] decryptData = RSA.decryptWithPrivateKeyBlock(encryptData, Base64.decode(privateKeyBase64Data, Base64.DEFAULT));
            Logger.dd("私钥解密耗时: " + (System.currentTimeMillis() - start));

            Logger.dd("私钥解密后长度: " + decryptData.length);
            Logger.dd("私钥数据还原为：" + new String(Base64.decode(decryptData, Base64.DEFAULT)));

            Logger.dd("==========================================================");

            encryptData = RSA.encryptWithPrivateKeyBlock(base64Data, Base64.decode(privateKeyBase64Data, Base64.DEFAULT));
            Logger.dd("私钥加密耗时: " + (System.currentTimeMillis() - start));
            Logger.dd("私钥加密后长度: " + encryptData.length);

            start = System.currentTimeMillis();
            decryptData = RSA.decryptWithPublicKeyBlock(encryptData, Base64.decode(publicKeyBase64Data, Base64.DEFAULT));
            Logger.dd("公钥解密耗时: " + (System.currentTimeMillis() - start));

            Logger.dd("公钥解密后长度: " + decryptData.length);
            Logger.dd("公钥数据还原为：" + new String(Base64.decode(decryptData, Base64.DEFAULT)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
