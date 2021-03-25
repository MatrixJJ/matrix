package com.sj.daily.ioc;

import org.springframework.stereotype.Component;

@Component
public class BussinessPersion implements  Person{
    //@Autowired(required = false)//根据by type 找到对应到bean
    private Animal animal = null ;
    //有猫有狗时 指定名称不报错
    //private Animal dog = null ;

//    public BussinessPersion (@Autowired @Qualifier("dog") Animal animal){
//        this.animal = animal ;
//    }
    @Override
    public void services() {
        this.animal.use();
    }

    @Override
    public void setAnimal(Animal animal) {
        this.animal = animal ;
    }
}
