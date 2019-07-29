package io.github.juanmuscaria.mintasm.bukkit;

public interface IReload {
    //Todo:fazer o Enable();

    /**
     * Executa a função de recarregar a class, sera chamando quando o plugin for recarregado.
     */
    void reload();

    /**
     * Executa a função de save() e para as ações da classe, sera chamando quando o plugin for Desabilitado.
     */
    void disable();

    /**
     * Executa a função de salvar os dados da classe, sera chamando periodicamente.
     */
    void save();

}
