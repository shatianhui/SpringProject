package hdu.sth.test4;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * description: Bean4 <br>
 * date: 2022/7/25 10:23 <br>
 * author: shatianhui <br>
 * version: 1.0 <br>
 */
@ConfigurationProperties(prefix = "java")
@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bean4 {
    private String home;
    private String version;
}