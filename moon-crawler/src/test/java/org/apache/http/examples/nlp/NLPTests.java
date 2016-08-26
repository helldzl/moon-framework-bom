package org.apache.http.examples.nlp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.moonframework.crawler.http.WebHttpClientUtils;
import org.moonframework.crawler.util.ObjectMapperFactory;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/7/14
 */
public class NLPTests {

    private CloseableHttpClient httpClient = WebHttpClientUtils.getInstance();
    private final String url = "http://api.fanyi.baidu.com/api/trans/vip/translate";
    private String appId = "20160714000025170";
    private String token = "1_6Cfd_s5GNjcu2lDRWb";
    private String salt = Integer.toString(17);
    private ObjectMapper objectMapper = ObjectMapperFactory.getInstance();

    public String md5(String q) {
        String md5 = DigestUtils.md5Hex(appId + q + salt + token);
        System.out.println(md5);
        return md5;
    }

    @Test
    public void google() throws IOException {
        String q = "In the early days of large recording consoles there were but three companies that were dominating the studios. Quad Eight was arguably considered as one of the best sounding at that time. The A Designs Audio \"Pacifica\" recaptures this sound with a preamp unlike any you have heard before. No wonder the Pacifica has been nominated for the Mix Magazine 2006 Tec Award!";
        q = "hello";
        String pattern = "http://translate.google.cn/translate_a/single?client=t&sl=en&tl=zh-CN&hl=en&dt=at&dt=bd&dt=ex&dt=ld&dt=md&dt=qca&dt=rw&dt=rm&dt=ss&dt=t&ie=UTF-8&oe=UTF-8&otf=1&ssel=0&tsel=0&pc=1&kc=2&tk=501994.102799&q={0}";
        String url = MessageFormat.format(pattern, q);
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

        HttpPost httpPost = new HttpPost("http://translate.google.cn/translate_a/single?client=t&sl=en&tl=zh-CN&hl=en&dt=at&dt=bd&dt=ex&dt=ld&dt=md&dt=qca&dt=rw&dt=rm&dt=ss&dt=t&ie=UTF-8&oe=UTF-8&source=btn&rom=1&ssel=0&tsel=0&kc=0&tk=431304.44459");
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("q", "<h2>A Sweet-sounding Cab for Your Favorite Guitar Amplifier</h2> \n" +
                "<p>65amps' Blue Series 1 x 12\" guitar cabinet is loaded with a G12H30 Celestion, giving you tons of punch and throaty detail. Blue Series guitar cabinets are perfectly matched to 65amps' heads and work great as extension cabinets for their combo amps. Constructed of void-free baltic birch, these open-back cabs sound crisp and punchy and can stand up to the abuse of your busiest road schedule. Solid joints also deliver superior responsiveness and articulation. Your cabs are intrinsic to your tone. Go pro with 65amps' Blue Series 1 x 12\" cab.</p> \n" +
                "<div></div> \n" +
                "<strong>65amps: awesome amplifiers and cabinets made by guitarists, for guitarists</strong> \n" +
                "<p>The founders of 65amps, Dan Boul and Peter Stroud, are a couple of accomplished guitarists who turned to their passion for guitar tone when they created amps and cabs such as this Blue Series 1 x 12\" cab. Drawing from Stroud's extensive experience onstage with Sheryl Crow, 65amps came about as an attempt to create roadworthy guitar amplifiers capable of keeping stage volume low while providing the rich and complex sound of full-sized amps. Since hitting the scene in 2002, 65amps models including the Blue Series 1 x 12\" cab have made their way into the rigs of guitarists such as Bruce Springsteen, Elvis Costello, Peter Frampton, Vince Gill, and Keith Urban.</p> \n" +
                "<strong>65amps Blue Series 1 x 12\" Guitar Cabinet Features:</strong> \n" +
                "<ul> \n" +
                " <li>1 x 12\" Celestion G12H30 speaker for rich, punchy tone </li> \n" +
                " <li>High-quality construction with void-free baltic birch </li> \n" +
                " <li>Open-back design with tuned horizontal port to maximize efficiency and punch </li> \n" +
                " <li>Superior robustness, responsiveness, and articulation</li> \n" +
                "</ul> \n" +
                "<p><span>Realize your amp's full potential with 65amps' Blue Series 1 x 12\" cab!</span></p> \n" +
                "<h3>Tech Specs</h3> \n" +
                "<table> \n" +
                " <tbody> \n" +
                "  <tr> \n" +
                "   <th>Configuration</th> \n" +
                "   <td>1 x 12\"</td> \n" +
                "  </tr> \n" +
                "  <tr> \n" +
                "   <th>Speakers</th> \n" +
                "   <td>1 x 12\" Celestion G12H30</td> \n" +
                "  </tr> \n" +
                "  <tr> \n" +
                "   <th>Power Handling</th> \n" +
                "   <td>30W</td> \n" +
                "  </tr> \n" +
                "  <tr> \n" +
                "   <th>Impedance</th> \n" +
                "   <td>16 ohms</td> \n" +
                "  </tr> \n" +
                "  <tr> \n" +
                "   <th>Cabinet Type</th> \n" +
                "   <td>Straight</td> \n" +
                "  </tr> \n" +
                "  <tr> \n" +
                "   <th>Open/Closed Back</th> \n" +
                "   <td>Open</td> \n" +
                "  </tr> \n" +
                "  <tr> \n" +
                "   <th>Construction Material</th> \n" +
                "   <td>Baltic Birch</td> \n" +
                "  </tr> \n" +
                "  <tr> \n" +
                "   <th>Mono/Stereo</th> \n" +
                "   <td>Mono</td> \n" +
                "  </tr> \n" +
                "  <tr> \n" +
                "   <th>Height</th> \n" +
                "   <td>19.5\"</td> \n" +
                "  </tr> \n" +
                "  <tr> \n" +
                "   <th>Width</th> \n" +
                "   <td>21.5\"</td> \n" +
                "  </tr> \n" +
                "  <tr> \n" +
                "   <th>Depth</th> \n" +
                "   <td>13\"</td> \n" +
                "  </tr> \n" +
                "  <tr> \n" +
                "   <th>Weight</th> \n" +
                "   <td>47 lbs.</td> \n" +
                "  </tr> \n" +
                "  <tr> \n" +
                "   <th>Manufacturer Part Number</th> \n" +
                "   <td>1x12\" BLUE</td> \n" +
                "  </tr> \n" +
                " </tbody> \n" +
                "</table> \n" +
                "<h3>Blue Series 1x12\" Cabinet - with G12H30 Image Gallery</h3> \n" +
                "<ul> \n" +
                " <li><a href=\"http://www.sweetwater.com/images/items/750/65B112G12-large.jpg\"><img src=\"http://www.sweetwater.com/images/items/120/65B112G12-medium.jpg\" width=\"120\" height=\"109\"></a></li> \n" +
                " <li><a href=\"http://www.sweetwater.com/images/closeup/750-65B112G12_detail1.jpg\"><img src=\"http://www.sweetwater.com/images/closeup/120-65B112G12_detail1.jpg\" width=\"120\" height=\"107\"></a></li> \n" +
                " <li><a href=\"http://www.sweetwater.com/images/closeup/750-65B112G12_detail2.jpg\"><img src=\"http://www.sweetwater.com/images/closeup/120-65B112G12_detail2.jpg\" width=\"120\" height=\"112\"></a></li> \n" +
                " <li><a href=\"http://www.sweetwater.com/images/closeup/750-65B112G12_detail3.jpg\"><img src=\"http://www.sweetwater.com/images/closeup/120-65B112G12_detail3.jpg\" width=\"120\" height=\"105\"></a></li> \n" +
                " <li><a href=\"http://www.sweetwater.com/images/closeup/750-65B112G12_detail4.jpg\"><img src=\"http://www.sweetwater.com/images/closeup/120-65B112G12_detail4.jpg\" width=\"120\" height=\"107\"></a></li> \n" +
                " <li><a href=\"http://www.sweetwater.com/images/closeup/750-65B112G12_detail5.jpg\"><img src=\"http://www.sweetwater.com/images/closeup/120-65B112G12_detail5.jpg\" width=\"120\" height=\"74\"></a></li> \n" +
                " <li><a href=\"http://www.sweetwater.com/images/closeup/750-65B112G12_detail6.jpg\"><img src=\"http://www.sweetwater.com/images/closeup/120-65B112G12_detail6.jpg\" width=\"120\" height=\"78\"></a></li> \n" +
                "</ul>"));
        httpPost.setEntity(new UrlEncodedFormEntity(pairs, Consts.UTF_8));

        try {
            String page = httpClient.execute(httpPost, response -> {
                String result = EntityUtils.toString(response.getEntity());
                return result;
                //return objectMapper.readValue(response.getEntity().getContent(), TransEntity.class);
            });
           // Document document = Jsoup.parse(page);
           // String text = document.select("#result_box").text();
            System.out.println(page);
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void npl(String q) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("q", q));
        pairs.add(new BasicNameValuePair("from", "en"));
        pairs.add(new BasicNameValuePair("to", "zh"));
        pairs.add(new BasicNameValuePair("appId", appId));
        pairs.add(new BasicNameValuePair("salt", salt));
        pairs.add(new BasicNameValuePair("sign", md5(q)));
        httpPost.setEntity(new UrlEncodedFormEntity(pairs, Consts.UTF_8));

        TransEntity result = httpClient.execute(httpPost, response -> {
            // System.out.println(EntityUtils.toString(response.getEntity()));
            return objectMapper.readValue(response.getEntity().getContent(), TransEntity.class);
        });
        System.out.println(result);
    }

    public static void main(String[] args) {
        NLPTests t = new NLPTests();
        try {
            //t.npl("A Designs Pacifica - If you're looking for a preamp with the depth of a Neve, the clear midrange of API, and the top end of a Massenburg, you'll find all that in the Pacifica by A Designs. In fact, that's what people who have access to all of the above are saying about the Pacifica. A Designs Pacifica brings back the sound of the Quad Eight console of the '70s (Pink Floyd's The Wall and Boston's More Than a Feeling were mixed on Quad Eight), which in its day was considered the best of the top three - the other two being Neve and API. If you're a small studio that can only afford one really good preamp, give the A Designs Pacifica some serious consideration.\nIn the early days of large recording consoles there were but three companies that were dominating the studios. Quad Eight was arguably considered as one of the best sounding at that time. The A Designs Audio \"Pacifica\" recaptures this sound with a preamp unlike any you have heard before. No wonder the Pacifica has been nominated for the Mix Magazine 2006 Tec Award!");
            t.npl("I am a big fan of A Designs gear. In addition to Pete being a really nice guy, the pieces he designs are freakishly good values. I have owned an MP2A for years already and it is an amazingly versatile 2 channel tube pre. As good as it is, the Pacifica has very quickly become my \"desert island\" pre. I have never put anything through it that it did handle beautifully. It has a clear yet hefty, forward sound that sits perfectly in the mix. ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
