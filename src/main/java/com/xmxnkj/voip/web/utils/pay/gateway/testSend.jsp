<%@ page import="java.util.Map" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="org.apache.commons.codec.digest.DigestUtils" %>
<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String url = "http://api.fulaiyuanju.cn/pay/gateWay.do";

    String merType = "01";

    String orderNo = "B" + System.currentTimeMillis();
    String tranChannel = "1";

    String txnAmt = "1";

    String merUrl = "http://118.178.19.140:8091/testpay.jsp";
    String pageUrl = "http://118.178.19.140:8091/testpay.jsp";


    String merData = "1";

    String merchantNo = "PC00000003H";
    String mackey = "53390857e45c160f1b19c6910f72225e";

    StringBuilder sb = new StringBuilder();


    sb.append(merchantNo);
    sb.append(orderNo);
    sb.append(merType);
    sb.append(tranChannel);
    sb.append(txnAmt);
    sb.append(pageUrl);
    sb.append(merUrl);
    sb.append(merData);

    String sign1 = DigestUtils.md5Hex(sb.toString() + mackey);


    Map<String, String> data = new HashMap<String, String>();

    data.put("merType",merType);
    data.put("orderNo",orderNo);
    data.put("tranChannel",tranChannel);
    data.put("txnAmt",txnAmt);
    data.put("merUrl",merUrl);
    data.put("pageUrl",pageUrl);
    data.put("merData",merData);
    data.put("merchantNo",merchantNo);
    data.put("sign",sign1);




%>
<html>
<!--[if lt IE 10]>
<script>alert("为了更好的体验，不支持IE10以下的浏览器。请选择google chrome 或者 firefox 浏览器。");
location.href = "http://www.ielpm.com";</script>
<![endif]-->
<script src="http://libs.baidu.com/jquery/1.11.3/jquery.js"></script>
<body>
<form name="form" id="form" action="<%=url %>" method="post">
    <% String tmp = "";
        for (Iterator<String> it = data.keySet().iterator(); it.hasNext(); ) {
            tmp = it.next();
    %>
    <input type="hidden" name="<%=tmp%>" value='<%=data.get(tmp)%>'/>
    <%}%>
</form>

<script>
    $(function () {
        $("#form").submit();
    })
</script>
</body>
</html>
