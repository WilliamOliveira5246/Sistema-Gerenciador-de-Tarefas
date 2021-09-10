package gerenciador;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class Tarefa {

	private int id;
	private String titulo;
	private String descricao;
	private String responsavel;
	private String prioridade;
	private String deadline;
	private String situacao;
	
	List<String> opcPrioridades;
	List<String> opcResponsaveis;
	List<String> opcSituacoes;
	
	public Tarefa() {
		opcPrioridades = new ArrayList<>();
		opcPrioridades.add("");
		opcPrioridades.add("alta");
		opcPrioridades.add("média");
		opcPrioridades.add("baixa");
		
		opcResponsaveis = new ArrayList<>();
		opcResponsaveis.add("");
		opcResponsaveis.add("Ana");
		opcResponsaveis.add("Breno");
		opcResponsaveis.add("Carlos");
		
		opcSituacoes = new ArrayList<>();
		opcSituacoes.add("");
		opcSituacoes.add("Em andamento");
		opcSituacoes.add("Concluido");
		
		this.deadline = "dd/mm/aaaa";
		this.situacao = "Em andamento";
	}

	public Tarefa(int id, String titulo, String descricao, String responsavel, String prioridade, String deadline, String situacao) {
		this.id = id;
		this.titulo = titulo;
		this.descricao = descricao;
		this.responsavel = responsavel;
		this.prioridade = prioridade;
		this.deadline = deadline;
		this.situacao = situacao;
		
		opcPrioridades = new ArrayList<>();
		opcPrioridades.add("");
		opcPrioridades.add("alta");
		opcPrioridades.add("média");
		opcPrioridades.add("baixa");
		
		opcResponsaveis = new ArrayList<>();
		opcResponsaveis.add("");
		opcResponsaveis.add("Ana");
		opcResponsaveis.add("Breno");
		opcResponsaveis.add("Carlos");
		
		opcSituacoes = new ArrayList<>();
		opcSituacoes.add("");
		opcSituacoes.add("Em andamento");
		opcSituacoes.add("Concluido");
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public String getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(String prioridade) {
		this.prioridade = prioridade;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	
	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public List<String> getOpcPrioridades() {
		return opcPrioridades;
	}
	
	public List<String> getOpcResponsaveis(){
		return opcResponsaveis;
	}
	
	public List<String> getOpcSituacoes() {
		return opcSituacoes;
	}

	@Override
	public String toString() {
		return "Tarefa [id=" + id +
				", titulo=" + titulo +
				", descricao=" + descricao +
				", responsavel=" + responsavel +
				", prioridade=" + prioridade +
				", deadline=" + deadline +
				"situacao" + situacao + "]";
	}
	
}
