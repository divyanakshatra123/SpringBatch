package com.example.demo.config;

import com.example.demo.model.User;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

@Configuration
@EnableBatchProcessing
public class DemoConfig {

    @Bean
    public Job job(JobBuilderFactory factory, StepBuilderFactory stepBuilderFactory, ItemReader<User> reader, ItemProcessor<User,User> processor , ItemWriter<User> writer){
        Step step = stepBuilderFactory.get("ETL_Step").
                <User,User>chunk(100).
                reader(reader).
                processor(processor).
                writer(writer).build();
        return factory.get("ETL").incrementer(new RunIdIncrementer()).start(step).build();
    }

  @Bean
  public FlatFileItemReader<User> reader(@Value("classpath:C:/Users/divyav/Downloads/demo/demo/src/main/resources/users.csv") Resource resource) {
      FlatFileItemReader<User> flatFileItemReader = new FlatFileItemReader<>();
      flatFileItemReader.setResource(new FileSystemResource("src/main/resources/users.csv"));

      flatFileItemReader.setLinesToSkip(1);
      flatFileItemReader.setLineMapper(LineMapper());
      flatFileItemReader.setName("line-Mapper");
      return flatFileItemReader;


  }

    private LineMapper<User> LineMapper() {
        DefaultLineMapper<User> userDefaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setDelimiter(",");
        delimitedLineTokenizer.setStrict(false);
        delimitedLineTokenizer.setNames("id","name","dept","salary");
        BeanWrapperFieldSetMapper<User> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        beanWrapperFieldSetMapper.setTargetType(User.class);
        userDefaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
        userDefaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);
        return userDefaultLineMapper;


    }
}
