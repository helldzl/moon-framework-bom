package org.moonframework.validation;

/**
 * @author quzile
 * @version 1.0
 * @since 2015/12/16
 */
public class ValidationGroups {

    /**
     * <p>基于给定的表述信息，在当前资源的下一级创建新的表述</p>
     * <p>对现有资源的注解。</p>
     * <p>向布告栏、新闻组、邮件列表或类似的文章组发布消息。</p>
     * <p>向数据处理流程提供例如表单提交结果的数据块。</p>
     * <p>通过追加操作来扩充数据库。</p>
     * <p>[不安全, 不幂等]</p>
     * <p>可能的HTTP STATUS CODE:[201 Created, 200 OK]</p>
     */
    public interface Post {
    }

    /**
     * <p>用给定的资源表述替换资源的当前状态</p>
     * <p>[不安全, 幂等]</p>
     * <p>可能的HTTP STATUS CODE:[200 OK, 204 no content]</p>
     */
    public interface Put {
    }

    /**
     * <p>销毁某个资源</p>
     * <p>[不安全, 幂等]</p>
     * <p>可能的HTTP STATUS CODE:[200 OK, 202 Accepted, 204 no content]</p>
     */
    public interface Delete {
    }

    /**
     * <p>获取资源的某个表述,将资源的表述提供给我.</p>
     * <p>[安全, 幂等]</p>
     * <p>可能的HTTP STATUS CODE:[200 OK, 300 moved permanently, 404 not found]</p>
     */
    public interface Get {
    }

    /**
     * <p>根据提供的表述信息修改资源的部分状态</p>
     * <p>[不安全, 不幂等], 考虑到PATCH的语义协议，他和POST一样不是幂等的</p>
     * <p>可能的HTTP STATUS CODE:[200 OK, 204 no content]</p>
     */
    public interface Patch {
    }

    /**
     * <p>获取服务器发送过来的报头信息</p>
     */
    public interface Head {
    }

    /**
     * <p>获取这个资源所能相应的HTTP方法列表</p>
     */
    public interface Options {
    }

    /*
     * 互联网草案中的
     */

    /**
     * <p>将其他资源链接到当前资源</p>
     * <p>[不安全, 幂等]</p>
     */
    public interface Link {
    }

    /**
     * <p>销毁当前资源和其他某些资源的链接关系</p>
     * <p>[不安全, 幂等]</p>
     */
    public interface Unlink {
    }

}
