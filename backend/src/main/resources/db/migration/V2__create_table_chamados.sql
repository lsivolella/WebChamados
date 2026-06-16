CREATE TABLE IF NOT EXISTS chamados (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    descricao VARCHAR(255) NOT NULL,
    prioridade VARCHAR(30) NOT NULL,
    status VARCHAR(30) NOT NULL,
    responsavel_id INTEGER NOT NULL,
    data_abertura TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_chamado_responsavel FOREIGN KEY (responsavel_id) REFERENCES responsaveis(id)
);