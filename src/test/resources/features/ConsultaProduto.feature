#language: pt
#encoding: utf8

Funcionalidade: Consultar Produto
  Como um cliente eventual, gostaria de consultar a disponibilidade
  e preço de alguns produtos que tenho interesse em adquirir
  palavra entre aspas eh um parametro
  depois de pronto o cenario, executar e pegar o trecho de snippets no log e colar na nova classe java, dentro do pacote

  Cenario: Consulta Produto Fixo com Enter
    Dado que acesso o site da Petz
    Quando procuro por "Ração" e pressiono Enter
    Entao exibe a lista de produtos relacionados a "Ração"
    Quando seleciono o primeiro produto da lista
    Entao verifico que a marca como "Royal Canin" com preco normal de "R$ 302,59" e "R$ 272,33" para assinantes

  Cenario: Consulta Produto Fixo com Clique na Lupa
    Dado que acesso o site da Petz
    Quando procuro por "Ração" e clico na Lupa
    Entao exibe a lista de produtos relacionados a "Ração"
    Quando seleciono o primeiro produto da lista
    Entao verifico que a marca como "Royal Canin" com preco normal de "R$ 302,59" e "R$ 272,33" para assinantes

  Cenario: Consulta Produto Fixo que Não Existe com Enter
    Dado que acesso o site da Petz
    Quando procuro por "001" e pressiono Enter
    Entao exibe uma lista de produtos aproximados e a mensagem de que nao encontrou "001"

  Cenario: Consulta Produto Fixo que Não Existe com Menos de 3 Letras
    Dado que acesso o site da Petz
    Quando procuro por "ab" e pressiono Enter
    Entao exibe uma caixa de alerta dizendo que precisa preencher pelo menos tres letras no termo
