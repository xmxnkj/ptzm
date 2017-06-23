<%@page import="com.hsit.common.uac.entity.Operation"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%
	List<Operation> operations = (List<Operation>)request.getSession().getAttribute("operations");
	String pid = request.getParameter("pid").toUpperCase(); //"3d41980c-299a-4219-8e87-54f17da9b502";
	int grade = 2;
	
	List<Operation> opts = new ArrayList<Operation>();
	for(int i=0; i<operations.size(); i++){
		String opPid = operations.get(i).getCategoryId();
		//log("pid="+request.getParameter("pid")+"---id="+operations.get(i).getCategoryId());
		if(opPid!=null){
			opPid = opPid.toUpperCase();
		}
		if(pid!=null && pid.equals(opPid)){
			opts.add(operations.get(i));
		}
	}
	System.out.println("size:" + opts.size());
	
	String selected = request.getParameter("selected");
	if(selected==null){
		selected="";
	}
	String selected4 = request.getParameter("selected4");
	int index4=-1;
	List<Operation> opts4 = null;
	boolean hasEdit=false;
	if(!StringUtils.isEmpty(selected)){
		try{
			int index = Integer.valueOf(selected);
			Operation operate =null;
			for(int i=0; i<opts.size(); i++){
				if(opts.get(i).getDisplayOrder()==index){
					operate = opts.get(i);
					index = i+1;
					break;
				}
			}
			//Operation operate = opts.get(index-1);
			opts4 = new ArrayList<Operation>();
			int grade4 = 3;
			for(int i=0; i<operations.size(); i++){
				if(operate.getId()!=null && operate.getId().equals(operations.get(i).getCategoryId())){
					opts4.add(operations.get(i));
					if("edit".equals(operations.get(i).getUrlPath())){
						hasEdit=true;
					}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	List<Operation> opts5 = null;
	if(opts4!=null && opts4.size()>0 && !StringUtils.isEmpty(selected4)){
		try{
			int index = Integer.valueOf(selected4);
			Operation operate = null;
			for(int i=0; i<opts4.size(); i++){
				if(opts4.get(i).getDisplayOrder()==index){
					operate = opts4.get(i);
					index = i+1;
					break;
				}
			}
			index4=index;
			//Operation operate = opts4.get(index-1);
			opts5 = new ArrayList<Operation>();
			int grade5 = 4;
			for(int i=0; i<operations.size(); i++){
				if(operate.getId()!=null && operate.getId().equals(operations.get(i).getCategoryId())){
					opts5.add(operations.get(i));
					if("edit".equals(operations.get(i).getUrlPath())){
						hasEdit=true;
					}
				}
			}
			
			System.out.println("selected4:"+index + ",opts5:" + opts5.size());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	String css="btn";
%>
<%if(!StringUtils.isEmpty(selected)) {%>
<div class="ope-bar"  style="margin-top:-10px;margin-left:-10px;">
	<%
		Boolean flag = true;
		for(int i=0; i<opts.size(); i++){
			Operation opt = opts.get(i);
			if(selected.equals(String.valueOf(opt.getDisplayOrder()))){
				css="btn2 active";
				flag = false;
			}else{
				css="btn";
			}
	%>
		<a id="css-<%=i %>" class="<%=css %>" href="${pageContext.request.contextPath}/<%=opt.getUrlPath() %>"><%=opt.getName() %></a>
	<%
		}
		if(flag){
			for(int i=0; i<opts.size(); i++){
				Operation opt = opts.get(i);
				if(selected.equals(String.valueOf(i+1))){
		%>
			<script type="text/javascript">
				$("#css-"+<%=i %>).attr("class","btn2 active"); 
			</script>
		<%
					break;
				}
			}
		}
	%>

</div>
<%} %>

<% if(opts5!=null){%>
	<div id="tb" style="padding:7px 5px;background-color: #f5f5f5;width:100%;" class="fl">
		<%for(int i=0; i<opts4.size(); i++){ 
			if(i+1==index4){
				css="btn2 active";
			}else{
				css="btn";
			}
			Operation opt = opts4.get(i);
		%>
			<a class="<%=css %>" href="${pageContext.request.contextPath}/<%=opt.getUrlPath() %>"><%=opt.getName() %></a>
		<%} %>
		<%for(int i=0; i<opts5.size(); i++){ 
			Operation opt = opts5.get(i);
			if(i==0){
		%>
			&nbsp;&nbsp;&nbsp;
			<%} %>
			<i class="line"></i>
			<a href="#" class="easyui-linkbutton" iconCls="<%=opt.getActionClassName() %>" plain="true" data-options="onClick:<%=opt.getUrlPath()%>"><%=opt.getName() %></a>
		<%} %>
    </div>
<%}else{ %>
	<script type="text/javascript">
	<%if(StringUtils.isEmpty(selected)){
	%>
		toolbars=[];
		if (typeof (params['isTotal'])!='undefined') {
			toolbars.push({
				iconCls:'icon-undo',
				text:'导出',
				handler:exportExcel
			})
			toolbars.push({
				iconCls:'icon-search',
				text:'搜索',
				handler:showSearch
			})
		}
	<%
		for(int i=0; i<opts.size(); i++){
	%>
			toolbars.push({
				iconCls:'<%=opts.get(i).getActionClassName()%>',
				text:'<%=opts.get(i).getName()%>',
				handler:<%=opts.get(i).getUrlPath()%>
			});
	<%	}%>
	<% }else if(opts4!=null && opts4.size()>0){%>
		toolbars=[];
		toolbars_1=[];
		toolbars_2=[];
		<%for(int i=0; i<opts4.size(); i++){%>
			<% String type = request.getParameter("type");
				if(type==null){type="";}
				if(type.equals("sale") || type.equals("work")){
					if(i==0){
				%>
					toolbars_1.push({
						iconCls:'<%=opts4.get(i).getActionClassName()%>',
						text:'<%=opts4.get(i).getName()%>',
						handler:<%=opts4.get(i).getUrlPath()%>
					})
				<%}else{%>
					toolbars_2.push({
						iconCls:'<%=opts4.get(i).getActionClassName()%>',
						text:'<%=opts4.get(i).getName()%>',
						handler:<%=opts4.get(i).getUrlPath()%>
					})
				<%}%>					
			<%}else{%>
				toolbars.push({
					iconCls:'<%=opts4.get(i).getActionClassName()%>',
					text:'<%=opts4.get(i).getName()%>',
					handler:<%=opts4.get(i).getUrlPath()%>
				})
			<%}%>
		<%}%>
		
	<%}else {%>
	<%}%>
	</script>
<%} %>
<%if(!hasEdit){%>
<script type="text/javascript">
<!--
//-->
</script>
<%}%>
<%-- <c:if test="${!empty params['isTotal']}">
	<script type="text/javascript">
		$(function(){
		    var len = $(".fl").find("a").length;
			var aurl = "&startTime="+params['startTime']+"&endTime="+params['endTime'];
			if (len>0) {
				var obj = $(".fl").find("a");
				obj.each(function(){
					var url = $(this).attr("href");
					if (url.indexOf("/billItem/") > -1) {
						$(this).attr("href",url+"&billTimeLower_s="+params['startTime']+"&billTimeUpper_s="+params['endTime']+aurl);
					}else if (url.indexOf("/customerCard/") > -1) {
						$(this).attr("href",url+"&endDateLower="+params['startTime']+"&endDateUpper="+params['endTime']+aurl);
					}
				});
			}
		})
	</script>
</c:if> --%>