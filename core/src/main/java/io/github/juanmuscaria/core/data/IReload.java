package io.github.juanmuscaria.core.data;

public interface IReload {
    //Todo:fazer o Enable();

    /**
     * Executa a função de recarregar a class, sera chamando quando o plugin for recarregado.
     */
    void Reload();

    /**
     * Executa a função de Save() e para as ações da classe, sera chamando quando o plugin for Desabilitado.
     */
    void Disable();

    /**
     * Executa a função de salvar os dados da classe, sera chamando periodicamente.
     */
    void Save();

}
