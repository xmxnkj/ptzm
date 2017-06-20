package com.xmszit.voip.voice.web;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.hsit.common.service.AppBaseService;
import com.xmszit.voip.voice.entity.VoiceTemplate;
import com.xmszit.voip.voice.entity.query.VoiceTemplateQuery;
import com.xmszit.voip.voice.service.IVoiceTemplateService;
import com.xmszit.voip.web.BaseController;
import com.xmszit.voip.web.models.ResultJson;
import com.xmszit.voip.web.models.ResultType;

@Controller
@RequestMapping("/voice/template")
public class VoiceTemplateController extends BaseController<VoiceTemplate, VoiceTemplateQuery, VoiceTemplate> {

	@Autowired
	private IVoiceTemplateService service;

	@Override
	protected IVoiceTemplateService getService() {
		// TODO Auto-generated method stub
		return service;
	}

	/**
	 * 根据id查询一条数据
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/findById")
	@ResponseBody
	public ResultJson findById(String id) {
		ResultJson rj = new ResultJson();
		try {
			VoiceTemplate voice = service.findById(id);
			if (voice != null) {
				rj.setSuccess(true);
				rj.setMessage("成功");
				rj.setEntity(voice);
				return rj;
			} else {
				rj.setSuccess(false);
				rj.setMessage("查无数据");
				return rj;
			}
		} catch (Exception e) {
			rj.setSuccess(false);
			rj.setMessage("失败，" + e.getMessage());
			return rj;
		}
	}

//	/**
//	 * 查询全部数据
//	 * 
//	 * @return
//	 */
//	@RequestMapping("/find")
//	@ResponseBody
//	public ResultJson find() {
//		ResultJson rj = new ResultJson();
//		try {
//			List<VoiceTemplate> voices = service.find();
//			if (voices != null && voices.size() > 0) {
//				rj.setSuccess(true);
//				rj.setMessage("成功");
//				rj.setEntity(voices);
//				return rj;
//			} else {
//				rj.setSuccess(false);
//				rj.setMessage("查无数据");
//				return rj;
//			}
//		} catch (Exception e) {
//			rj.setSuccess(false);
//			rj.setMessage("失败，" + e.getMessage());
//			return rj;
//		}
//	}

	/**
	 * 添加数据
	 * 
	 * @param voice
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public ResultJson add(VoiceTemplate voice) {
		ResultJson rj = new ResultJson();
		try {
			if(voice.getName()==null&&voice.getName().equals("")) {
				rj.setSuccess(false);
				rj.setMessage("名称不能为空");
				return rj;
			}
			if(voice.getDescription()==null||voice.getDescription().equals("")) {
				rj.setSuccess(false);
				rj.setMessage("请选择文件");
				return rj;
			}
			if(getLoginClientUser()==null) {
				rj.setSuccess(false);
				rj.setMessage("请重新登录");
				return rj;
			}
			voice.setClientId(getLoginClientId());
			voice.setPerson(getLoginClientUser());
			voice.setUploadDate(new Date());
			service.add(voice);
			rj.setSuccess(true);
			rj.setMessage("添加成功");
			return rj;
		} catch (Exception e) {
			rj.setSuccess(false);
			rj.setMessage("添加失败，" + e.getMessage());
			return rj;
		}
	}

	/**
	 * 更新数据
	 * 
	 * @param id
	 * @param name
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public ResultJson update(String id, String name) {
		ResultJson rj = new ResultJson();
		try {
			VoiceTemplate voice = service.findById(id);
			if (voice != null) {
				voice.setName(name);
				service.add(voice);
				rj.setSuccess(true);
				rj.setMessage("更新成功");
				return rj;
			} else {
				rj.setSuccess(false);
				rj.setMessage("该id不存在");
				return rj;
			}

		} catch (Exception e) {
			rj.setSuccess(false);
			rj.setMessage("更新失败，" + e.getMessage());
			return rj;
		}
	}

	/**
	 * 删除数据
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public ResultJson delete(String id) {
		ResultJson rj = new ResultJson();
		try {
			service.deleteById(id);
			rj.setSuccess(true);
			rj.setMessage("删除成功");
			return rj;
		} catch (Exception e) {
			rj.setSuccess(false);
			rj.setMessage("删除失败，" + e.getMessage());
			return rj;
		}
	}
	
	
	@RequestMapping("/uploadFile")
	@ResponseBody
	public ResultJson uploadFile(MultipartFile file,String id, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		ResultJson LJ = new ResultJson();
		try {
			if (!file.isEmpty()) {
				String path = null;// 文件路径
				String type = null;// 文件类型
				String fileName = file.getOriginalFilename();// 文件原名称
				System.out.println("上传的文件原名称:" + fileName);
				// 判断文件类型
				type = fileName.indexOf(".") != -1
						? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length())
						: null;
				if (type != null) {
					if ("ZIP".equals(type.toUpperCase()) || "RAR".equals(type.toUpperCase())) {
						// 项目在容器中实际发布运行的根路径
						String realPath = request.getSession().getServletContext().getRealPath("/")
								+ "upload/file/voice/";
						// 自定义的文件名称
						String trueFileName = String.valueOf(System.currentTimeMillis())+"."+type.toUpperCase();// + fileName;
					
						System.out.println("存放文件的路径:" + realPath);
						File f = new File(realPath);
						 //判断目标文件所在的目录是否存在  
				        if(!f.exists()) {  
				            //如果目标文件所在的目录，则创建  
				        	 f.mkdirs();
				        }  
				    	// 设置存放文件的路径
						path = realPath + trueFileName;
						// 转存文件到指定的路径
						file.transferTo(new File(path));
						System.out.println("文件成功上传到指定目录下");
						
						String basePath = "/" + "upload/file/voice/";
						String filePath = basePath + trueFileName;
						Map<String,String> map = new HashMap<>();
						map.put("filePath", filePath);
						LJ.setSuccess(true);
						LJ.setMessage("上传成功");
						LJ.setEntity(map);
						return LJ;
					} else {
						System.out.println("不是我们想要的文件类型,请按要求重新上传");
						LJ.setMessage(type.toUpperCase() + "不是我们想要的文件类型,请按要求重新上传");
						LJ.setSuccess(false);
						return LJ;
					}
				} else {
					System.out.println("文件类型为空");
					LJ.setMessage("文件类型为空");
					LJ.setSuccess(false);
					return LJ;
				}
			} else {
				System.out.println("没有找到相对应的文件");
				LJ.setMessage("没有找到相对应的文件");
				LJ.setSuccess(false);
				return LJ;
			}
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LJ.setSuccess(false);
			LJ.setMessage("文件上传失败，错误代号：1，错误信息：" + e.getMessage());
			return LJ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LJ.setSuccess(false);
			LJ.setMessage("文件上传失败，错误代号：2，错误信息：" + e.getMessage());
			return LJ;
		}
	}

}
