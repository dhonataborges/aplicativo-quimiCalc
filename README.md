# 📱 QuimiCalc

<p align="center">
Aplicativo desenvolvido para auxiliar operadores de ETA (Estação de Tratamento de Água) no cálculo rápido e preciso de dosagens de produtos químicos utilizados no tratamento de água.
</p>

---

# 📌 Sobre o Projeto

O **QuimiCalc** foi criado com o objetivo de **facilitar o trabalho operacional em Estações de Tratamento de Água (ETA)**, permitindo que operadores realizem cálculos de dosagens químicas de forma **rápida, segura e padronizada**.

Durante a operação de uma ETA, cálculos incorretos podem impactar diretamente na qualidade do tratamento da água. O aplicativo busca **reduzir erros humanos**, automatizando o processo de cálculo com base nos parâmetros informados pelo operador.

---

# ⚙️ Funcionalidades

## 🧪 Cadastro de Produtos

Permite registrar os produtos químicos utilizados no tratamento da água.

### Informações cadastradas

- Nome do produto
- Densidade
- Concentração

Esses dados ficam armazenados no sistema e são utilizados posteriormente nos cálculos de dosagem.

---

## 📊 Cálculo de Dosagens

Nesta funcionalidade o operador pode calcular a quantidade necessária de produto químico com base nas condições operacionais da estação.

### Fluxo do cálculo

1. Selecionar o **produto químico cadastrado**
2. Informar a **vazão de água que está entrando na ETA**
3. Informar a **dosagem desejada do produto**
4. O sistema realiza automaticamente o **cálculo da dosagem**

O resultado é apresentado em **mg/L ou mL**, facilitando a aplicação prática no processo de tratamento.

---

## 📸 Telas do Aplicativo

<p align="center">
  <img src="images/Tela Inicial.jpeg" width="300">
  <img src="images/Tela Cadastro de Produtos.jpeg" width="300">
  <img src="images/Tela Calcular Dosagem.jpeg" width="300">
</p>

---

# 🎯 Objetivo

O **QuimiCalc** foi desenvolvido para:

- Auxiliar operadores de ETA no dia a dia
- Padronizar cálculos de dosagem química
- Reduzir erros operacionais
- Agilizar a tomada de decisão no tratamento da água
- Facilitar a rotina de operação em estações de tratamento

---

# 🏗 Arquitetura do Projeto

O projeto segue uma organização baseada no padrão **MVVM (Model - View - ViewModel)**, que permite melhor separação de responsabilidades, maior organização do código e facilidade de manutenção.
## Estrutura de pacotes

```
app
├── manifests
│   └── AndroidManifest.xml
│
├── java
│   └── com.alfasistemastecnologia.appeta
│       ├── database
│       ├── model
│       ├── repository
│       ├── ui.produto
│       ├── viewmodel
│       ├── FirstFragment
│       ├── SecondFragment
│       └── MainActivity
│
└── res
    ├── drawable
    ├── font
    ├── layout
    │   ├── activity_calculadora.xml
    │   ├── activity_main.xml
    │   ├── activity_produto.xml
    │   ├── fragment_first.xml
    │   ├── fragment_second.xml
    │   └── item_produto.xml
    ├── menu
    ├── mipmap
    ├── navigation
    ├── values
    └── xml
```
