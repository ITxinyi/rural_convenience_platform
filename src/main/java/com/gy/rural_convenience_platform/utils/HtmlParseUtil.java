package com.gy.rural_convenience_platform.utils;

import com.gy.rural_convenience_platform.entity.Goods;
import com.gy.rural_convenience_platform.entity.GoodsImg;
import com.gy.rural_convenience_platform.mapper.GoodsMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Component
public class HtmlParseUtil {

    @Autowired
    private GoodsMapper goodsMapper;

    public List<Goods> getHtmlByKey(String key) throws IOException {
        String url = "https://search.jd.com/Search?keyword=" + key;
        Document document = Jsoup.parse(new URL(url),30000);

        List<Goods> list = new ArrayList<>();

        Element element = document.getElementById("J_goodsList");
        Elements elements = element.getElementsByTag("li");
        for (Element el : elements) {

            Goods goods = new Goods();

//            String img = el.getElementsByTag("img").eq(0).attr("data-lazy-img");
            String price = el.getElementsByClass("p-price").eq(0).text();
            String title = el.getElementsByClass("p-name").eq(0).text();

            goods.setGoodsTitle(title);
            goods.setPrice(Double.valueOf(price));

            String itemUrl = el.getElementsByClass("p-img").eq(0).first().child(0).attr("href");
            itemUrl = "https:" + itemUrl;
            Document imgDoc = Jsoup.parse(new URL(itemUrl),30000);

            Element specList = imgDoc.getElementById("spec-list");
            Elements imgs = specList.getElementsByTag("img");

            int stopFlag = 1;
            GoodsImg goodsImg = new GoodsImg();
            for (Element imgEl : imgs) {
                if (stopFlag > 4) break;
                String imgdata = imgEl.attr("data-url");
                imgdata = "https://img10.360buyimg.com/n1/" + imgdata;

                if (stopFlag == 1) goodsImg.setImg1(imgdata);
                if (stopFlag == 2) goodsImg.setImg2(imgdata);
                if (stopFlag == 3) goodsImg.setImg3(imgdata);
                if (stopFlag == 4) goodsImg.setImg4(imgdata);

                System.out.println(imgdata);
                stopFlag += 1;
            }

            goods.setGoodsImg(goodsImg);
            list.add(goods);
        }
        return list;
    }


    public void main(String[] args) throws IOException {
        List<Goods> list = new HtmlParseUtil().getHtmlByKey("新鲜蔬菜");
        for (Goods goods : list) {

        }
        System.out.println(list);
    }

}
