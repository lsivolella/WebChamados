import { useState } from 'react';
import { fecharChamado } from '../services/api';

const PRIORIDADE_CORES = {
    BAIXA: 'bg-green-100 text-green-800',
    MEDIA: 'bg-yellow-100 text-yellow-800',
    ALTA: 'bg-red-100 text-red-800',
};

const STATUS_CORES = {
    ABERTO: 'bg-blue-100 text-blue-800',
    EM_ANDAMENTO: 'bg-purple-100 text-purple-800',
    RESOLVIDO: 'bg-green-100 text-green-800',
    FECHADO: 'bg-gray-100 text-gray-800',
};

const PRIORIDADE_LABELS = {
    BAIXA: 'Baixa',
    MEDIA: 'Média',
    ALTA: 'Alta',
};

const STATUS_LABELS = {
    ABERTO: 'Aberto',
    EM_ANDAMENTO: 'Em andamento',
    RESOLVIDO: 'Resolvido',
    FECHADO: 'Fechado',
};

function ChamadoTable({ chamados, onEditar, onAtualizar }) {
    const [menuAberto, setMenuAberto] = useState(null);

    async function handleFechar(id) {
        if (!window.confirm('Deseja fechar este chamado?')) return;
        await fecharChamado(id);
        setMenuAberto(null);
        await onAtualizar();
    }

    function toggleMenu(id) {
        setMenuAberto(menuAberto === id ? null : id);
    }

    if (chamados.length === 0) {
        return (
            <div className="bg-white rounded shadow p-8 text-center text-gray-500">
                Nenhum chamado encontrado.
            </div>
        );
    }

    return (
        <div className="bg-white rounded shadow overflow-hidden">
            <table className="min-w-full divide-y divide-gray-200">
                <thead className="bg-gray-50">
                    <tr>
                        {['Título', 'Prioridade', 'Status', 'Responsável', 'Abertura', 'Atualização', 'Ações'].map(col => (
                            <th
                                key={col}
                                className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                            >
                                {col}
                            </th>
                        ))}
                    </tr>
                </thead>
                <tbody className="divide-y divide-gray-200">
                    {chamados.map(chamado => (
                        <tr key={chamado.id} className="hover:bg-gray-50">
                            <td className="px-6 py-4 text-sm text-gray-900">{chamado.titulo}</td>
                            <td className="px-6 py-4">
                                <span className={`px-2 py-1 rounded-full text-xs font-medium ${PRIORIDADE_CORES[chamado.prioridade] ?? 'bg-gray-100 text-gray-800'}`}>
                                    {PRIORIDADE_LABELS[chamado.prioridade] ?? chamado.prioridade}
                                </span>
                            </td>
                            <td className="px-6 py-4">
                                <span className={`px-2 py-1 rounded-full text-xs font-medium ${STATUS_CORES[chamado.status] ?? 'bg-gray-100 text-gray-800'}`}>
                                    {STATUS_LABELS[chamado.status] ?? chamado.status}
                                </span>
                            </td>
                            <td className="px-6 py-4 text-sm text-gray-700">{chamado.responsavelNome}</td>
                            <td className="px-6 py-4 text-sm text-gray-500">
                                {new Date(chamado.dataAbertura).toLocaleString('pt-BR')}
                            </td>
                            <td className="px-6 py-4 text-sm text-gray-500">
                                {new Date(chamado.dataAtualizacao).toLocaleString('pt-BR')}
                            </td>
                            <td className="px-6 py-4 text-sm">
                                <div className="flex gap-2">
                                    <button
                                        onClick={() => onEditar(chamado)}
                                        className="px-3 py-1 text-xs text-blue-600 border border-blue-300 rounded hover:bg-blue-50"
                                    >
                                        Editar
                                    </button>
                                    <button
                                        onClick={() => handleFechar(chamado.id)}
                                        className="px-3 py-1 text-xs text-red-600 border border-red-300 rounded hover:bg-red-50"
                                    >
                                        Fechar
                                    </button>
                                </div>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}

export default ChamadoTable;