package top.cnwdi.Sakta_hospitalSys;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication
@MapperScan("top.cnwdi.Sakta_hospitalSys.mapper")
public class SaktaHospitalSysApplication {

	public static void main(String[] args) {
		SpringApplication.run(SaktaHospitalSysApplication.class, args);
	}

}
