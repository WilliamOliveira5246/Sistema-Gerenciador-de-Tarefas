package gerenciador;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped

public class TarefaController {

	private List<Tarefa> tarefas;
	private TarefaDBUtil tarefaDBUtil;
	private Logger logger = Logger.getLogger(getClass().getName());
	
	public TarefaController() throws Exception {
		tarefas = new ArrayList<>();
		tarefaDBUtil = TarefaDBUtil.getInstance();
	}
	
	public List<Tarefa> getTarefas(){
		return tarefas;
	}
	
	public void loadTarefas() {
		logger.info("Loading tarefas");
		tarefas.clear();
		
		try {
			tarefas = tarefaDBUtil.getTarefas();
		}
		catch (Exception exc) {
			logger.log(Level.SEVERE, "Error loading tarefas", exc);
			addErrorMessage(exc);
		}
	}
	
	public void loadSearchTarefas(int id,String titulo,String descricao, String responsavel, String situacao) {
		logger.info("Loading search tarefas");
		tarefas.clear();
		
		try {
			tarefas = tarefaDBUtil.buscarTarefas(id,titulo,descricao, responsavel,situacao);
		}
		catch (Exception exc) {
			logger.log(Level.SEVERE, "Error loading tarefas", exc);
			addErrorMessage(exc);
		}
	}
	
	public String addTarefa(Tarefa theTarefa) {
		logger.info("Adding tarefa: " + theTarefa);
		
		try {
			tarefaDBUtil.addTarefa(theTarefa);
		}
		catch(Exception exc) {
			logger.log(Level.SEVERE, "Error adding tarefa", exc);
			addErrorMessage(exc);
			return null;
		}
		
		return "list-tarefas?faces-redirect=true";
	}
	
	public String loadTarefa(int tarefaId) {
		logger.info("loading tarefa: " + tarefaId);
		
		try {
			Tarefa theTarefa = tarefaDBUtil.getTarefa(tarefaId);
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();		
			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put("tarefa", theTarefa);
		}
		catch(Exception exc) {
			logger.log(Level.SEVERE, "Error loading tarefa id:" + tarefaId, exc);
			addErrorMessage(exc);
			return null;
		}
		
		return "update-tarefa-form.xhtml";
	}
	
	public String updateTarefa(Tarefa theTarefa) {
		logger.info("updating tarefa: " + theTarefa);
		
		try {
			tarefaDBUtil.updateTarefa(theTarefa);
			
		} catch (Exception exc) {
			logger.log(Level.SEVERE, "Error updating tarefa: " + theTarefa, exc);
			addErrorMessage(exc);
			return null;
		}
		
		return "list-tarefas?faces-redirect=true";	
	}
	
	public String deleteTarefa(int tarefaId) {
		logger.info("Deleting tarefa id: " + tarefaId);
		
		try {
			tarefaDBUtil.deleteTarefa(tarefaId);
		}
		catch(Exception exc) {
			logger.log(Level.SEVERE, "Error deleting tarefa id: " + tarefaId, exc);
			addErrorMessage(exc);
			return null;
		}
		
		return "list-tarefas";
	}
	
	public String concluirTarefa(Tarefa theTarefa) {
		logger.info("Concluindo tarefa: " + theTarefa.getId());
		
		try {
			tarefaDBUtil.concluirTarefa(theTarefa);
		}
		catch(Exception exc) {
			logger.log(Level.SEVERE, "Error conclusao tarefa: " + theTarefa.getId(), exc);
			addErrorMessage(exc);
			return null;
		}
		
		return "list-tarefas";
	}
	
	private void addErrorMessage(Exception exc) {
		FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

}
