const BASE_URL = 'http://localhost:8080';

export async function listarChamados() {
    const response = await fetch(`${BASE_URL}/chamados`);
    return response.json();
}

export async function listarResponsaveis() {
    const response = await fetch(`${BASE_URL}/responsaveis`);
    return response.json();
}

export async function criarChamado(data) {
    const response = await fetch(`${BASE_URL}/chamados`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data),
    });
    return response.json();
}

export async function atualizarChamado(id, data) {
    const response = await fetch(`${BASE_URL}/chamados/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data),
    });
    return response.json();
}

export async function fecharChamado(id) {
    await fetch(`${BASE_URL}/chamados/${id}`, {
        method: 'DELETE',
    });
}