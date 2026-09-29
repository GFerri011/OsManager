package br.com.osmanager.osmanager_backend.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
public class PublicDocumentController {

    @GetMapping("/terms")
    public String terms() {
        return """
                TERMOS DE USO — OS MANAGER

                1. Objetivo

                O OS Manager é uma aplicação destinada ao gerenciamento
                de chamados e solicitações direcionadas ao departamento
                de Tecnologia da Informação.

                2. Responsabilidade do usuário

                O usuário é responsável pela veracidade das informações
                fornecidas ao utilizar a aplicação.

                3. Utilização da plataforma

                A plataforma deve ser utilizada exclusivamente para fins
                relacionados ao gerenciamento de chamados de suporte de TI.

                4. Segurança

                O usuário não deve compartilhar suas credenciais de acesso
                com terceiros.

                5. Aceitação

                Ao aceitar estes Termos de Uso, o usuário declara estar
                de acordo com as condições apresentadas.
                """;
    }

    @GetMapping("/privacy-policy")
    public String privacyPolicy() {
        return """
                POLÍTICA DE PRIVACIDADE — OS MANAGER

                1. Dados coletados

                A aplicação poderá coletar dados necessários para identificação
                do usuário, autenticação e gerenciamento dos chamados.

                2. Finalidade

                Os dados são utilizados para permitir o funcionamento da
                aplicação, autenticação, gerenciamento dos chamados,
                segurança e auditoria das operações.

                3. Segurança

                As credenciais de acesso são armazenadas utilizando mecanismos
                de proteção adequados, incluindo hash de senha.

                4. Registro de operações

                Determinadas operações poderão ser registradas para fins
                de auditoria e segurança da aplicação.

                5. Direitos do titular

                O titular poderá solicitar informações relacionadas ao
                tratamento de seus dados pessoais, observadas as condições
                aplicáveis à operação da plataforma.

                6. Aceitação

                Ao aceitar esta Política de Privacidade, o usuário declara
                estar ciente das informações apresentadas.
                """;
    }
}