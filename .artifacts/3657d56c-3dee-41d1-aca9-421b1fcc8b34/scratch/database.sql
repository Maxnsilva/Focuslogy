CREATE DATABASE IF NOT EXISTS focuslogy;
USE focuslogy;

CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    metaDiariaMinutos INT DEFAULT 60,
    dataCadastro BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS materias (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuarioId INT NOT NULL,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT,
    cor VARCHAR(7) NOT NULL,
    metaSemanalMinutos INT NOT NULL,
    dataCriacao BIGINT NOT NULL,
    FOREIGN KEY (usuarioId) REFERENCES usuarios(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS sessoes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuarioId INT NOT NULL,
    materiaId INT NOT NULL,
    assunto VARCHAR(255) NOT NULL,
    duracaoMinutos INT NOT NULL,
    tecnica VARCHAR(50) NOT NULL,
    data BIGINT NOT NULL,
    status VARCHAR(20) DEFAULT 'CONCLUIDA',
    FOREIGN KEY (usuarioId) REFERENCES usuarios(id) ON DELETE CASCADE,
    FOREIGN KEY (materiaId) REFERENCES materias(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS metas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuarioId INT NOT NULL,
    materiaId INT,
    titulo VARCHAR(255) NOT NULL,
    minutosObjetivo INT NOT NULL,
    dataInicio BIGINT NOT NULL,
    dataFim BIGINT NOT NULL,
    status VARCHAR(20) DEFAULT 'ATIVA',
    FOREIGN KEY (usuarioId) REFERENCES usuarios(id) ON DELETE CASCADE,
    FOREIGN KEY (materiaId) REFERENCES materias(id) ON DELETE CASCADE
);
