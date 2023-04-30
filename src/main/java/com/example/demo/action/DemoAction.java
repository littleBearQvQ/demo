package com.example.demo.action;

import com.example.demo.zip.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Slf4j
@RestController
@RequestMapping("/action")
public class DemoAction {

    /*private static final Logger logger = LoggerFactory.getLogger(DemoAction.class);*/

    @GetMapping("/123")
    public String content(){
        log.info("run success");
        return "123";
    }

    @RequestMapping(value = "/uploadFile",method = RequestMethod.POST)
    @ResponseBody
//@RequestParam()注解的参数要和前端代码name属性值对应
    public String file(@RequestParam("file") MultipartFile multipartFile){
        //判断文件是否为空 isEmpty
        if (multipartFile == null){
            return "文件为空";
        }
        //获取文件的原名称 getOriginalFilename
        String OriginalFilename = multipartFile.getOriginalFilename();
        //获取时间戳和文件的扩展名，拼接成一个全新的文件名； 用时间戳来命名是为了避免文件名冲突
        //String fileName = System.currentTimeMillis()+"."+OriginalFilename.substring(OriginalFilename.lastIndexOf(".")+1);

        String fileName = FileUtil.getFilePrefixName(OriginalFilename)
                + System.currentTimeMillis()
                + "."
                + FileUtil.getFileSuffixName(OriginalFilename);

        //定义文件存放路径
        String filePath = "D:\\Works\\demo\\upload\\";
        //新建一个目录（文件夹）
        log.info("OriginalFilename:{}",OriginalFilename);
        log.info("fileName:{}",fileName);
        log.info("filePath:{}",filePath);
        File dest = new File(filePath+fileName);
        //判断filePath目录是否存在，如不存在，就新建一个
        if (!dest.getParentFile().canExecute()){
            dest.getParentFile().mkdirs(); //新建一个目录
        }
        try {
            //文件输出
            multipartFile.transferTo(dest);
        }
        catch ( Exception e) {
            e.printStackTrace();
            //拷贝失败要有提示
            return "上传失败";
        }
        return "上传成功";
    }



}
