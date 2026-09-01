# Plano de Refinamento Visual - Interface Limpa e Modesta

Este plano visa aprimorar o visual da tela inicial (**Dashboard**), eliminando o excesso de espaço em branco sem poluir a interface, mantendo um design **limpo, moderno e modesto**.

## User Review Required

> [!NOTE]
> **O que será adicionado para preencher a tela harmoniosamente:**
> 1. **Card "Dica & Inspiração de Foco"**: Uma frase motivacional sobre consistência nos estudos.
> 2. **Card "Última Atividade"**: Exibe automaticamente a última matéria/sessão estudada para dar vida ao aplicativo.
> 3. **Ícones nos Cards de Métricas**: Adição de ícones decorativos nos cards "Meta Diária", "Sessões" e "Tempo Total".
> 4. **Aprimoramento da Barra de Progresso**: Barra de progresso mais visível com cantos arredondados e cor de contraste.

---

## Proposta de Mudanças

### 1. Refinamento do Layout XML

#### [MODIFY] [fragment_dashboard.xml](file:///C:/Users/max57374586/AndroidStudioProjects/Focuslogy/app/src/main/res/layout/fragment_dashboard.xml)
- Adicionar ícones nos títulos das métricas (`Meta Diária`, `Sessões`, `Tempo Total`).
- Aumentar a espessura e destaque da barra de progresso da meta.
- **Novo Card de Dica/Frase de Foco**: Inserir abaixo dos botões de ação uma frase inspiradora com ícone.
- **Novo Card de Última Atividade**: Inserir seção mostrando a última matéria estudada.

---

### 2. Atualização do Fragment Java

#### [MODIFY] [DashboardFragment.java](file:///C:/Users/max57374586/AndroidStudioProjects/Focuslogy/app/src/main/java/com/example/focuslogy/ui/fragment/DashboardFragment.java)
- Vincular o Card de Última Atividade com os dados do `SessaoViewModel` para mostrar dinamicamente o último conteúdo estudado.

---

## Plano de Verificação

### Testes Manuais
1. **Verificação Visual**: Abrir a tela inicial e confirmar que o espaço em branco inferior foi preenchido de forma elegante.
2. **Dinâmica de Dados**: Fazer uma sessão de estudo e confirmar se a "Última Atividade" é atualizada automaticamente no Dashboard.
