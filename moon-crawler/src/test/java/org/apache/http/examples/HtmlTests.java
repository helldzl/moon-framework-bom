package org.apache.http.examples;

import com.googlecode.htmlcompressor.compressor.HtmlCompressor;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.junit.Test;
import org.moonframework.crawler.parse.HTMLClean;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/6/23
 */
public class HtmlTests {

    @Test
    public void clean2(){
        String result = HTMLClean.cleanHtml("<h2>A Sweet-sounding Cab for Your Favorite Guitar Amplifier</h2> \n" +
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
                "</ul>");

        System.out.println(result);
    }

    @Test
    public void clean() {
        // String html = "<!-- PDP-ProductCopy --> <a href='http://www.baidu.com'>　　百度</a><table width=100><tr width='100%'><td></td></tr></table> <div 　　　　 style='hello' width='100 200' class=\"col sharp-tl\" id=\"productDetail\">  <section id=\"product-overview\">   <div class=\"sectionTitle\">    Overview   </div>   <div class=\"sectionCopy\">    <!-- PDP-YouTubeViewer -->    <p class=\"description\">The MTD ZX 5-String Electric Bass Guitar is made for serious players. The ZX features a carved mahogany body with a maple-burl top finished in a translucent gloss black. The MTD bass comes loaded with custom Bartolini pickups and preamp, hand-installed in the U.S. It has a wide range of tones, and will fit perfectly in any genre of music.<br><br>The bass guitar''s neck has an asymmetrical curve, thicker on the bass side and thinner on the treble side, offering your fretting hand more comfortable placement. The ZX 5-string bass includes volume, pickup pan controls, as well as treble, mid, and bass EQ adjusters. The tone is incredibly full, with massive bottom end, which does not lose clarity or definition. The pickup position, string spacing, and low action make slapping the MTD ZX bass a dream. Cut the treble and add some bass EQ to produce a classic warm blues thump, or center the knobs and crank the ZX bass''s volume for crisp, metal tones.<br><br>Michael Tobias Design, located in Kinston, New York, is a manufacturer of fine handmade instrument and designer of imported instruments created for the discriminating musician.</p>    <div class=\"specs\">     <strong class=\"featuresHeading\">Features</strong>     <ul>      <li>Body wood: mahogany</li>      <li>Top wood: burled maple</li>      <li>Neck wood: maple</li>      <li>Frets: medium jumbo</li>      <li>Controls: volume, bend, 3-band EQ</li>      <li>Tuners: MTD closed gear</li>      <li>Hardware: smoky chrome</li>     </ul>    </div>   </div>  </section>  <section id=\"product-specifications\">   <div class=\"sectionTitle\">   Specifications  </div>   <div class=\"sectionCopy\">    <ul class=\"prodSpecs\">     <li>Strings: 5<br>Body type: Kingston<br>Weight: 19lb.<br>Scale length: 35\"<br>Neck joint: bolt-on<br>Neck wood: maple<br>Neck shape: asymmetric<br>Frets: medium jumbo<br>Nut width: 1-3/4\"<br>Bridge: MTD quick release<br>Bridge pickup: MTD active wood covered soapbar<br>Neck pickup: MTD active wood soapbar</li>    </ul>   </div>  </section> </div>";
        String html = "<img src=\"/images/items/120/LilWhiskeyH-medium.jpg\" width=\"120\" height=\"58\">";
        // System.out.println(html);

        HtmlCompressor compressor = new HtmlCompressor();

        compressor.setCompressCss(true);
        compressor.setRemoveIntertagSpaces(true);
        compressor.setRemoveComments(true);
        // compressor.setRemoveComments(true);

        // compress(compressor, html);
        System.out.println();
        clean(html);

    }

    private void clean(String html) {
        System.out.println(Jsoup.clean(html, Whitelist.relaxed()));
    }

    private void compress(HtmlCompressor compressor, String html) {
        System.out.println(compressor.compress(html));
    }
}
