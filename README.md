# Ranking de Obras - Java Swing

## Sobre o Projeto

Este é um sistema interativo de votação e ranking de obras desenvolvido em **Java Swing**. O projeto permite que os usuários comparem e votem entre diferentes obras, com um ranking atualizado em tempo real. O sistema conta com um **mecanismo dinâmico de desempate**, onde obras com menos votos são priorizadas para novas rodadas de votação.

## Funcionalidades

✅ Interface gráfica intuitiva com **Java Swing**\
✅ Lógica de desempate dinâmica para resolver empates\
✅ Atualização do ranking em tempo real\
✅ Alternância entre **Ranking Completo** e **Top 3 Destacado**\
✅ Botões interativos para facilitar a navegação

## 🛠️ Tecnologias Utilizadas

- **Java 8+**
- **Java Swing** para interface gráfica
- **Padrão Observer** para atualização automática da interface
- **Collections API** para manipulação de listas e ordenação

## 📸 Demonstração

### **Tela de Votação**

![image](https://github.com/user-attachments/assets/c0b62174-29e4-4449-8b92-19f98adfdf73)


### **Ranking Final**

![image](https://github.com/user-attachments/assets/c7cc5d38-3abf-4e43-83ca-a18ca46e9815)
![image](https://github.com/user-attachments/assets/564e674a-ed7a-4174-b956-e5a41293f330)


## 🚀 Como Executar o Projeto

### **Pré-requisitos**

- Ter o **Java 8+** instalado
- Ter um ambiente de desenvolvimento como **Eclipse** ou **IntelliJ IDEA**

### **Passo a Passo**

1. Clone este repositório:
   ```sh
   git clone https://github.com/seu-usuario/ranking-obras-java.git
   ```
2. Importe o projeto no Eclipse ou IntelliJ IDEA.
3. Compile e execute a classe `MainScreen.java`.
4. Comece a votar e acompanhe a evolução do ranking!

## 📌 Estrutura do Projeto

```
📂 br.com.rafael.ranking
 ┣ 📂 modelo   # Lógica do ranking
 ┃ ┣ 📜 Piece.java
 ┃ ┣ 📜 Ranking.java
 ┃ ┣ 📜 RankingObserver.java
 ┃ ┣ 📜 ToScore.java
 ┣ 📂 visao    # Interface gráfica
 ┃ ┣ 📜 MainScreen.java
 ┃ ┣ 📜 Display.java
 ┃ ┣ 📜 Keyboard.java
```

## 🤝 Contribuição

Contribuições são bem-vindas! Caso tenha sugestões ou melhorias, abra um **Pull Request** ou entre em contato.

## 📄 Licença

Este projeto está licenciado sob a **MIT License** - sinta-se à vontade para utilizá-lo e modificá-lo!

---

📌 **Criado por Rafael Teixeira**

