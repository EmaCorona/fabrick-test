package it.corona.fabrick.config;

import it.corona.fabrick.constant.BeanScope;
import it.corona.fabrick.mapper.TransactionMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class MapstructConfig {

    @Bean
    @Scope(BeanScope.PROTOTYPE)
    public TransactionMapper transactionMapper() {
        return Mappers.getMapper(TransactionMapper.class);
    }
}
