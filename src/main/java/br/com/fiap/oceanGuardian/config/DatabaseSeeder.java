package br.com.fiap.oceanGuardian.config;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.fiap.oceanGuardian.model.Blog;
import br.com.fiap.oceanGuardian.model.Endereco;
import br.com.fiap.oceanGuardian.model.Evento;
import br.com.fiap.oceanGuardian.model.Reporte;
import br.com.fiap.oceanGuardian.model.Usuario;
import br.com.fiap.oceanGuardian.repository.BlogRepository;
import br.com.fiap.oceanGuardian.repository.EnderecoRepository;
import br.com.fiap.oceanGuardian.repository.EventoRepository;
import br.com.fiap.oceanGuardian.repository.ReporteRepository;
import br.com.fiap.oceanGuardian.repository.UsuarioRepository;

@Configuration
@Profile("dev")
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    EnderecoRepository enderecoRepository;

    @Autowired
    ReporteRepository reporteRepository;

    @Autowired
    EventoRepository eventoRepository;

    @Override
    public void run(String... args) throws Exception {
        enderecoRepository.saveAll(
                List.of(
                        Endereco.builder()
                                .id(1)
                                .logradouro("Rua B")
                                .numero("456")
                                .complemento("Apto 2")
                                .bairro("Bairro B")
                                .cidade("Cidade B")
                                .estado("Estado B")
                                .cep("87654321")
                                .userId(1)
                                .build(),
                        Endereco.builder()
                                .id(2)
                                .logradouro("Rua A")
                                .numero("123")
                                .complemento("Apto 1")
                                .bairro("Bairro A")
                                .cidade("Cidade A")
                                .estado("Estado A")
                                .cep("12345678")
                                .userId(2)
                                .build(),
                        Endereco.builder()
                                .id(3)
                                .logradouro("Rua C")
                                .numero("789")
                                .bairro("Bairro C")
                                .cidade("Cidade C")
                                .estado("Estado C")
                                .cep("87654321")
                                .userId(3)
                                .build(),
                        Endereco.builder()
                                .id(4)
                                .logradouro("Rua D")
                                .numero("789")
                                .bairro("Bairro D")
                                .cidade("Cidade D")
                                .estado("Estado D")
                                .cep("87654321")
                                .userId(4)
                                .build()));

        usuarioRepository.saveAll(
                List.of(
                        Usuario.builder()
                                .id(1)
                                .nome("Usuário A")
                                .telefone("1234567890")
                                .email("usuarioa@example.com")
                                .senha("senha123")
                                .tipo("VOLUNTARIO")
                                .build(),
                        Usuario.builder()
                                .id(2)
                                .nome("Usuário B")
                                .telefone("0987654321")
                                .email("usuariob@example.com")
                                .senha("senha321")
                                .tipo("VOLUNTARIO")
                                .build(),
                        Usuario.builder()
                                .id(3)
                                .nome("ONG B")
                                .telefone("0987654321")
                                .email("ongb@example.com")
                                .senha("senha321")
                                .tipo("ONG")
                                .build(),
                        Usuario.builder()
                                .id(4)
                                .nome("ONG A")
                                .telefone("1234567890")
                                .email("onga@example.com")
                                .senha("senha123")
                                .tipo("ONG")
                                .build()));

        reporteRepository.saveAll(
                List.of(
                        Reporte.builder()
                                .id(1)
                                .descricao("Descrição do Reporte A")
                                .tipo("OLEO")
                                .nivel("CRITICO")
                                .latitude(-23.5505)
                                .longitude(-46.6333)
                                .data(LocalDate.of(2024, 5, 1))
                                .usuario(usuarioRepository.findById(1).get())
                                .build(),
                        Reporte.builder()
                                .id(2).descricao("Descrição do Reporte B")
                                .tipo("LIXO")
                                .nivel("ALTO")
                                .latitude(-22.9068)
                                .longitude(-43.1729)
                                .data(LocalDate.of(2024, 5, 2))
                                .usuario(usuarioRepository.findById(2).get())
                                .build()));

        eventoRepository.saveAll(
                List.of(
                        Evento.builder()
                                .id(1)
                                .nome("Projeto A")
                                .descricao("Descrição do Projeto A")
                                .localizacao("Localização A")
                                .data(LocalDate.of(2024, 6, 1))
                                .doacoes("Doações A")
                                .autor(usuarioRepository.findById(3).get())
                                .build(),
                        Evento.builder()
                                .id(2)
                                .nome("Projeto B")
                                .descricao("Descrição do Projeto B")
                                .localizacao("Localização B")
                                .data(LocalDate.of(2024, 7, 1))
                                .doacoes("Doações B")
                                .autor(usuarioRepository.findById(4).get())
                                .build()));

        blogRepository.saveAll(
                List.of(
                        Blog.builder()
                                .id(1)
                                .data(LocalDate.of(2024, 5, 1))
                                .artigo("A poluição plástica nos oceanos é uma crise ambiental global que ameaça a saúde dos ecossistemas marinhos e, por extensão, a saúde humana. Todos os anos, milhões de toneladas de plástico entram nos oceanos, resultando em efeitos devastadores sobre a vida marinha e o ambiente costeiro. Este artigo examina as causas, impactos e possíveis soluções para essa crescente preocupação ambiental.\r\n"
                                        + //
                                        "\r\n" + //
                                        "Causas da Poluição Plástica\r\n" + //
                                        "A poluição plástica nos oceanos é causada principalmente pelo descarte inadequado de resíduos plásticos. As principais fontes incluem:\r\n"
                                        + //
                                        "\r\n" + //
                                        "Resíduos Urbanos: Sacolas plásticas, garrafas, embalagens e outros resíduos descartados inadequadamente.\r\n"
                                        + //
                                        "Indústrias Pesqueiras: Redes de pesca e outros equipamentos descartados ou perdidos no mar.\r\n"
                                        + //
                                        "Atividades Marítimas: Descartes diretos de navios e plataformas de petróleo.\r\n"
                                        + //
                                        "Impactos na Vida Marinha\r\n" + //
                                        "Os impactos da poluição plástica nos oceanos são numerosos e incluem:\r\n"
                                        + //
                                        "\r\n" + //
                                        "Ingestão e Emaranhamento: Muitos animais marinhos, como tartarugas, aves e peixes, ingerem plástico, confundindo-o com alimento, o que pode resultar em bloqueios intestinais e morte. Redes de pesca e outros detritos plásticos também podem emaranhar animais marinhos, levando a lesões ou morte.\r\n"
                                        + //
                                        "Bioacumulação: Microplásticos podem entrar na cadeia alimentar marinha, bioacumulando-se em organismos e, eventualmente, chegando aos humanos, com potenciais efeitos tóxicos.\r\n"
                                        + //
                                        "Destruição de Habitats: Resíduos plásticos podem destruir habitats sensíveis, como recifes de coral e manguezais, afetando a biodiversidade local.\r\n"
                                        + //
                                        "Soluções e Iniciativas\r\n" + //
                                        "Para combater a poluição plástica, várias iniciativas estão sendo implementadas:\r\n"
                                        + //
                                        "\r\n" + //
                                        "Redução do Uso de Plásticos: Governos e empresas estão promovendo a redução do uso de plásticos descartáveis através de políticas de restrição e incentivos ao uso de alternativas biodegradáveis.\r\n"
                                        + //
                                        "Reciclagem e Reutilização: Programas de reciclagem eficazes são cruciais para reduzir a quantidade de plástico que chega aos oceanos. Incentivar a reutilização de materiais também é uma estratégia importante.\r\n"
                                        + //
                                        "Educação e Conscientização: Campanhas de educação pública são essenciais para mudar comportamentos e reduzir a poluição plástica.\r\n"
                                        + //
                                        "Limpeza dos Oceanos: Organizações como a Ocean Cleanup estão desenvolvendo tecnologias para remover plásticos dos oceanos e rios.\r\n"
                                        + //
                                        "Conclusão\r\n" + //
                                        "A poluição plástica nos oceanos é uma crise urgente que exige ação imediata. Governos, empresas e indivíduos devem trabalhar juntos para reduzir a quantidade de plástico descartado, promover a reciclagem e desenvolver novas soluções para limpar nossos oceanos. Somente através de um esforço conjunto podemos proteger a saúde dos ecossistemas marinhos e garantir um futuro sustentável para o nosso planeta.")
                                .titulo("A Poluição Plástica nos Oceanos: Um Desafio Global Urgente")
                                .autor(usuarioRepository.findById(3).get())
                                .build(),
                        Blog.builder()
                                .id(2)
                                .data(LocalDate.of(2024, 5, 2))
                                .artigo("Os oceanos são essenciais para a vida na Terra, regulando o clima, fornecendo alimento e sustentando uma biodiversidade rica. No entanto, a poluição química está colocando esses ecossistemas vitais em risco. Produtos químicos tóxicos provenientes de diversas fontes estão se acumulando nos oceanos, com efeitos devastadores sobre a vida marinha e potencialmente perigosos para a saúde humana. Este artigo explora as principais fontes de poluentes químicos nos oceanos, seus impactos e as medidas necessárias para mitigar essa ameaça.\r\n"
                                        + //
                                        "\r\n" + //
                                        "Fontes de Poluentes Químicos\r\n"
                                        + //
                                        "Os poluentes químicos chegam aos oceanos de várias maneiras, incluindo:\r\n"
                                        + //
                                        "\r\n" + //
                                        "Despejo Industrial: Substâncias químicas tóxicas, como metais pesados, PCBs e dioxinas, são frequentemente despejadas diretamente nos oceanos ou em rios que desembocam no mar.\r\n"
                                        + //
                                        "Agricultura: Pesticidas e fertilizantes utilizados na agricultura são lavados pela chuva e escoam para os rios, chegando eventualmente aos oceanos.\r\n"
                                        + //
                                        "Derramamentos de Petróleo: Acidentes com navios petroleiros e plataformas de perfuração liberam grandes quantidades de petróleo, que contamina vastas áreas oceânicas.\r\n"
                                        + //
                                        "Águas Residuais: O despejo de esgoto não tratado ou tratado inadequadamente pode introduzir uma variedade de poluentes químicos nos ambientes marinhos.\r\n"
                                        + //
                                        "Impactos na Vida Marinha\r\n"
                                        + //
                                        "A poluição química afeta a vida marinha de várias maneiras:\r\n"
                                        + //
                                        "\r\n" + //
                                        "Toxicidade Aguda e Crônica: Muitos poluentes químicos são altamente tóxicos para a vida marinha, causando morte imediata ou efeitos crônicos, como câncer e defeitos congênitos.\r\n"
                                        + //
                                        "Bioacumulação e Biomagnificação: Substâncias químicas tóxicas podem se acumular nos tecidos dos organismos marinhos, aumentando em concentração à medida que sobem na cadeia alimentar. Isso pode resultar em altos níveis de toxinas em predadores de topo, incluindo humanos que consomem frutos do mar.\r\n"
                                        + //
                                        "Alterações no Comportamento e Reprodução: Poluentes podem afetar o comportamento, a saúde reprodutiva e o desenvolvimento dos organismos marinhos, ameaçando populações inteiras.\r\n"
                                        + //
                                        "Medidas de Mitigação\r\n"
                                        + //
                                        "Para combater a poluição química dos oceanos, várias medidas devem ser implementadas:\r\n"
                                        + //
                                        "\r\n" + //
                                        "Regulamentação e Controle: Governos precisam estabelecer e fazer cumprir regulamentações mais rigorosas sobre o despejo de produtos químicos industriais e agrícolas nos corpos d'água.\r\n"
                                        + //
                                        "Tratamento de Águas Residuais: Melhorar a infraestrutura de tratamento de esgoto para garantir que os poluentes sejam removidos antes que a água seja liberada nos oceanos.\r\n"
                                        + //
                                        "Tecnologias de Limpeza: Desenvolver e implementar tecnologias para limpar derramamentos de petróleo e remover poluentes químicos dos oceanos.\r\n"
                                        + //
                                        "Monitoramento e Pesquisa: Investir em programas de monitoramento e pesquisa para entender melhor os impactos dos poluentes químicos e desenvolver estratégias eficazes de mitigação.\r\n"
                                        + //
                                        "Educação e Conscientização: Promover a conscientização pública sobre os efeitos da poluição química e incentivar práticas sustentáveis em indústrias e agricultura.\r\n"
                                        + //
                                        "Conclusão\r\n" + //
                                        "A poluição química nos oceanos é uma ameaça séria que exige uma resposta global coordenada. A proteção dos ecossistemas marinhos depende de ações firmes para regular o despejo de poluentes, melhorar o tratamento de águas residuais, desenvolver tecnologias de limpeza e aumentar a conscientização pública. Somente através de um esforço coletivo podemos garantir a saúde dos oceanos e a biodiversidade que eles sustentam, protegendo assim a saúde do nosso planeta e das gerações futuras.")
                                .titulo("O Impacto dos Poluentes Químicos nos Oceanos e Suas Consequências para a Vida Marinha")
                                .autor(usuarioRepository.findById(4).get())
                                .build()));

    }

}
