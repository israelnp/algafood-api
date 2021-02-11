package com.algaworks.algafood;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CadastroCozinhaIT.class, //test case 1
        CadastroRestauranteIT.class     //test case 2
})
public class SuiteTest {
}
