import { useState, useEffect } from 'react';
import { listarChamados, listarResponsaveis } from './services/api';
import ChamadoTable from './components/ChamadoTable';
import ChamadoModal from './components/ChamadoModal';

function App() {
  const [chamados, setChamados] = useState([]);
  const [responsaveis, setResponsaveis] = useState([]);
  const [modalAberto, setModalAberto] = useState(false);
  const [chamadoSelecionado, setChamadoSelecionado] = useState(null);

  async function carregarDados() {
    const [chamadosData, responsaveisData] = await Promise.all([
      listarChamados(),
      listarResponsaveis(),
    ]);
    setChamados(chamadosData);
    setResponsaveis(responsaveisData);
  }

  useEffect(() => {
    carregarDados();
  }, []);

  function abrirModalNovo() {
    setChamadoSelecionado(null);
    setModalAberto(true);
  }

  function abrirModalEditar(chamado) {
    setChamadoSelecionado(chamado);
    setModalAberto(true);
  }

  function fecharModal() {
    setModalAberto(false);
    setChamadoSelecionado(null);
  }

  async function onSucesso() {
    fecharModal();
    await carregarDados();
  }

  return (
    <div className="min-h-screen bg-gray-100">
      <header className="bg-white shadow">
        <div className="max-w-7xl mx-auto px-6 py-4 flex items-center justify-between">
          <h1 className="text-xl font-semibold text-gray-800">WebChamados</h1>
          <button
            onClick={abrirModalNovo}
            className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 text-sm"
          >
            Novo Chamado
          </button>
        </div>
      </header>

      <main className="max-w-7xl mx-auto px-6 py-8">
        <ChamadoTable
          chamados={chamados}
          onEditar={abrirModalEditar}
          onAtualizar={carregarDados}
        />
      </main>

      {modalAberto && (
        <ChamadoModal
          chamado={chamadoSelecionado}
          responsaveis={responsaveis}
          onSucesso={onSucesso}
          onFechar={fecharModal}
        />
      )}
    </div>
  );
}

export default App;