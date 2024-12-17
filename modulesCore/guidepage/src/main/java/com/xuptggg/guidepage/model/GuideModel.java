package com.xuptggg.guidepage.model;

import android.graphics.Color;

import com.xuptggg.guidepage.R;
import com.xuptggg.guidepage.contract.IGuideContract;

import java.util.ArrayList;
import java.util.List;

public class GuideModel implements IGuideContract.IGuideModel<String> {
    @Override
    public void execute(String data, LoadGuideInfoCallBack callBack) {

        List<GuideInfo> list = new ArrayList<>();

        list.add(new GuideInfo(Color.parseColor("#BFE1CA"),R.drawable.pic_tea_show1
                ,"赌书消得","泼茶香"
                , "        谁念西风独自凉，萧萧黄叶闭疏窗，沉思往事立残阳。\n" +
                "       被酒莫惊春睡重，赌书消得泼茶香，当时只道是寻常。"));

        list.add(new GuideInfo(Color.parseColor("#A5CEBD"),R.drawable.pic_tea_show3
                ,"啜苦咽甘","茶也"
                , "卷而舒，则本其始，又炙之。若火干者，以气熟止；日干者，以柔止。"));

        list.add(new GuideInfo(Color.parseColor("#608B7A"),R.drawable.pic_tea_show2
                ,"且将新火试新茶","诗酒趁年华"
                , "春未老，风细柳斜斜。试上超然台上看，半壕春水一城花。烟雨暗千家。\n"
                + "寒食后，酒醒却咨嗟。休对故人思故国，且将新火试新茶。诗酒趁年华。"));

        list.add(new GuideInfo(Color.parseColor("#BFE1CA"),R.drawable.pic_tea_show4
                ,"竹炉汤沸","火初红"
                , "寒夜客来茶当酒，竹炉汤沸火初红。\n" +
                "寻常一样窗前月，才有梅花便不同。"));

        callBack.onSuccess(list);
    }
}
