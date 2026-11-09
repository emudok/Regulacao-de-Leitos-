% Base de conhecimento complementar do sistema de regulação.

hospital(central, 'Hospital Central').
hospital(regional, 'Hospital Regional').

leito(central, 101, clinico, disponivel).
leito(central, 102, uti, ocupado).
leito(regional, 201, clinico, disponivel).

solicitacao(maria, cardiologia, urgente, central).
solicitacao(joao, uti, vaga_zero, central).
solicitacao(ana, pediatria, normal, regional).

prioridade(vaga_zero, 3).
prioridade(urgente, 2).
prioridade(normal, 1).

leito_disponivel(Hospital, Tipo) :-
    leito(Hospital, _, Tipo, disponivel).

encaminhar(Paciente, Hospital, Tipo) :-
    solicitacao(Paciente, Tipo, Prioridade, Hospital),
    prioridade(Prioridade, _),
    leito_disponivel(Hospital, Tipo).

% Exemplos de consultas:
% ?- hospital(Id, Nome).
% ?- leito_disponivel(central, Tipo).
% ?- solicitacao(Paciente, _, vaga_zero, Hospital).
% ?- encaminhar(Paciente, Hospital, Tipo).
