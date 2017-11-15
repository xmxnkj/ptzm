package com.xmxnkj.voip.system.controllers;

import com.xmxnkj.voip.system.entity.ShowImage;
import com.xmxnkj.voip.system.entity.query.ShowImageQuery;
import com.xmxnkj.voip.system.service.ShowImageService;
import com.xmxnkj.voip.web.BaseController;
import com.xmxnkj.voip.web.models.ListJson;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping({"/back/showImage"})
public class ShowImageController
  extends BaseController<ShowImage, ShowImageQuery>
{
  @Autowired
  private ShowImageService service;
  
  public ShowImageService getService()
  {
    return this.service;
  }
  
  @RequestMapping({"showImageJsp"})
  public String showImageJsp()
  {
    return "showImg/imgList";
  }
  
  @RequestMapping({"editImageJsp"})
  public String editImageJsp()
  {
    return "showImg/imgEdit";
  }
  
  @RequestMapping({"del"})
  @ResponseBody
  public ListJson delImageJsp(HttpServletRequest request, ShowImage si){
	  ListJson lijs = new ListJson();
   try{
	    service.deleteById(si.getId());
	    lijs.setSuccess(Boolean.valueOf(true));
	    lijs.setMessage("删除成功!");
	    String realPath = request.getSession().getServletContext().getRealPath("/");
	    String path = realPath + "/upload/lunbo/" + si.getUrl();
	    File file = new File(path);
	    if (file.exists()) {
	      file.delete();
	    }
	    return lijs;
   }catch(Exception e){
	   
   }
   return lijs;
  }
  
  @RequestMapping({"upload"})
  @ResponseBody
  public ListJson upload(MultipartFile file, String id, Integer sortNo, HttpServletRequest request)
    throws IllegalStateException, IOException{
    String trueFileName = "";
    ShowImage SI = null;
    if ((id != null) && (!id.equals("")))
    {
      SI = (ShowImage)this.service.getById(id);
      SI.setSort_No(sortNo);
      trueFileName = SI.getUrl();
    }
    if (file != null){
      String path = null;
      String type = null;
      String fileName = file.getOriginalFilename();
      System.out.println("上传的文件原名称:" + fileName);
      
      type = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) : null;
      if (type != null){
        if (("GIF".equals(type.toUpperCase())) || 
          ("PNG".equals(type.toUpperCase())) || 
          ("JPG".equals(type.toUpperCase()))){
          String realPath = request.getSession().getServletContext().getRealPath("/");
          if (SI == null){
            SI = new ShowImage();
            SI.setSort_No(sortNo);
            SI.setUrl(UUID.randomUUID() + "." + type);
          }
          trueFileName = SI.getUrl();
          
          path = realPath + "/upload/lunbo/" + trueFileName;
          System.out.println("存放图片文件的路径:" + path);
          
          file.transferTo(new File(path));
          this.service.save(SI);
          System.out.println("文件成功上传到指定目录下");
          return new ListJson(true);
        }
        System.out.println("不是我们想要的文件类型,请按要求重新上传");
        return new ListJson(false);
      }
      System.out.println("文件类型为空");
      return new ListJson(false);
    }
    System.out.println("没有找到相对应的文件");
    

    return new ListJson(false);
  }
  
  @RequestMapping({"getAll"})
  @ResponseBody
  public ListJson getAll()
  {
    List<ShowImage> list = this.service.getEntities(null);
    ListJson LJ = new ListJson();
    LJ.setRows(list);
    return LJ;
  }
}
