import { useState, useEffect } from 'react';
import { criarChamado, atualizarChamado } from '../services/api';

const PRIORIDADES = ['BAIXA', 'MEDIA', 'ALTA'];
const STATUS = ['ABERTO', 'EM_ANDAMENTO', 'RESOLVIDO', 'FECHADO'];

function ChamadoModal({ chamado, responsaveis, onSucesso, onFechar }) {
    const editando = chamado !== null;

    const [form, setForm] = useState({
        titulo: '',
        descricao: '',
        prioridade: 'BAIXA',
        status: 'ABERTO',
        responsavelId: '',
        atribuicaoAutomatica: false,
    });

    const [carregando, setCarregando] = useState(false);
    const [erro, setErro] = useState(null);

    useEffect(() => {
        if (editando) {
            setForm({
                titulo: chamado.titulo,
                descricao: chamado.descricao,
                prioridade: chamado.prioridade,
                status: chamado.status,
                responsavelId: chamado.responsavelId,
                atribuicaoAutomatica: false,
            });
        }
    }, [chamado]);

    function handleChange(e) {
        const { name, value, type, checked } = e.target;
        setForm(prev => ({
            ...prev,
            [name]: type === 'checkbox' ? checked : value,
        }));
    }

    async function handleSubmit(e) {
        e.preventDefault();
        setCarregando(true);
        setErro(null);

        try {
            const payload = {
                titulo: form.titulo,
                descricao: form.descricao,
                prioridade: form.prioridade,
                status: form.status,
                responsavelId: form.atribuicaoAutomatica ? null : Number(form.responsavelId),
                atribuicaoAutomatica: form.atribuicaoAutomatica,
            };

            if (editando) {
                await atualizarChamado(chamado.id, payload);
            } else {
                await criarChamado(payload);
            }

            await onSucesso();
        } catch (err) {
            setErro('Erro ao salvar chamado. Verifique os dados e tente novamente.');
        } finally {
            setCarregando(false);
        }
    }

    return (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50">
            <div className="bg-white rounded shadow-lg w-full max-w-lg mx-4">
                <div className="flex items-center justify-between px-6 py-4 border-b">
                    <h2 className="text-lg font-semibold text-gray-800">
                        {editando ? 'Editar Chamado' : 'Novo Chamado'}
                    </h2>
                    <button onClick={onFechar} className="text-gray-400 hover:text-gray-600 text-xl">
                        ×
                    </button>
                </div>

                <form onSubmit={handleSubmit} className="px-6 py-4 space-y-4">
                    <div>
                        <label className="block text-sm font-medium text-gray-700 mb-1">Título</label>
                        <input
                            name="titulo"
                            value={form.titulo}
                            onChange={handleChange}
                            required
                            className="w-full border border-gray-300 rounded px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
                        />
                    </div>

                    <div>
                        <label className="block text-sm font-medium text-gray-700 mb-1">Descrição</label>
                        <textarea
                            name="descricao"
                            value={form.descricao}
                            onChange={handleChange}
                            required
                            rows={3}
                            className="w-full border border-gray-300 rounded px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
                        />
                    </div>

                    <div className="grid grid-cols-2 gap-4">
                        <div>
                            <label className="block text-sm font-medium text-gray-700 mb-1">Prioridade</label>
                            <select
                                name="prioridade"
                                value={form.prioridade}
                                onChange={handleChange}
                                className="w-full border border-gray-300 rounded px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
                            >
                                {PRIORIDADES.map(p => (
                                    <option key={p} value={p}>{p}</option>
                                ))}
                            </select>
                        </div>

                        {editando && (
                            <div>
                                <label className="block text-sm font-medium text-gray-700 mb-1">Status</label>
                                <select
                                    name="status"
                                    value={form.status}
                                    onChange={handleChange}
                                    className="w-full border border-gray-300 rounded px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
                                >
                                    {STATUS.map(s => (
                                        <option key={s} value={s}>{s}</option>
                                    ))}
                                </select>
                            </div>
                        )}
                    </div>

                    <div>
                        <label className="flex items-center gap-2 text-sm font-medium text-gray-700 mb-2">
                            <input
                                type="checkbox"
                                name="atribuicaoAutomatica"
                                checked={form.atribuicaoAutomatica}
                                onChange={handleChange}
                                className="rounded"
                            />
                            Atribuir automaticamente ao responsável com menos chamados em aberto
                        </label>

                        {!form.atribuicaoAutomatica && (
                            <select
                                name="responsavelId"
                                value={form.responsavelId}
                                onChange={handleChange}
                                required
                                className="w-full border border-gray-300 rounded px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
                            >
                                <option value="">Selecione um responsável</option>
                                {responsaveis.map(r => (
                                    <option key={r.id} value={r.id}>{r.nome}</option>
                                ))}
                            </select>
                        )}
                    </div>

                    {erro && (
                        <p className="text-sm text-red-600">{erro}</p>
                    )}

                    <div className="flex justify-end gap-3 pt-2">
                        <button
                            type="button"
                            onClick={onFechar}
                            className="px-4 py-2 text-sm text-gray-600 border border-gray-300 rounded hover:bg-gray-50"
                        >
                            Cancelar
                        </button>
                        <button
                            type="submit"
                            disabled={carregando}
                            className="px-4 py-2 text-sm text-white bg-blue-600 rounded hover:bg-blue-700 disabled:opacity-50"
                        >
                            {carregando ? 'Salvando...' : 'Salvar'}
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
}

export default ChamadoModal;