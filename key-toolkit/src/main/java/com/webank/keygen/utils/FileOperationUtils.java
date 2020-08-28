/*
 * Copyright 2014-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.webank.keygen.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.web3j.utils.Numeric;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * FileUtils
 *
 * @Description: FileUtils
 * @author graysonzhang
 * @data 2019-07-14 18:59:01
 *
 */
@Slf4j
public class FileOperationUtils {
       
    public static void writeFile(String filePath, String content){
        File file = getFile(filePath);
        try {
            FileUtils.writeStringToFile(file, content, "UTF-8");
        } catch (IOException e) {
            log.error("file write error:{}", e.getMessage());
        }
    }

    public static void writeBinary(String filePath, String hex){
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(filePath);
            byte[] bytes = Numeric.hexStringToByteArray(hex);
            fOut.write(bytes, 0, bytes.length);
            fOut.flush();
        } catch (IOException e) {
            log.error("file write error:{}", e);
        }
        finally {
            if(fOut != null) {
                try {
                    fOut.close();
                }
                catch (Exception e) {
                }
            }
        }
    }
    
    public static File getFile(String filePath){
        File file = new File(filePath);
        if(!file.exists()){
            try {
                boolean success = file.createNewFile();
                if(!success){
                    log.error("create key data file fail ");
                }
            } catch (Exception e) {
                log.error("create key data file fail : {}.", e.getMessage());
            }
        }
        return file;
    }

    public static boolean exist(String filePath){
        if (StringUtils.isBlank(filePath)){
            return false;
        }
        File file = new File(filePath);
        return file.exists();
    }
    
    public static String generateFilePath(String fileName, String fileDir){
        if(fileDir.endsWith(File.separator)){
            return fileDir + fileName;
        }else{
            return fileDir + File.separator + fileName;
        }   
    }
}
