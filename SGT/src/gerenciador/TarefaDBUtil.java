package gerenciador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class TarefaDBUtil {

	private static TarefaDBUtil instance;
	private DataSource dataSource;
	private String jndiName = "java:comp/env/jdbc/gerenciador_tarefas";
	
	public static TarefaDBUtil getInstance() throws Exception {
		if (instance == null) {
			instance = new TarefaDBUtil();
		}
		
		return instance;
	}
	
	private TarefaDBUtil() throws NamingException {
		dataSource = getDataSource();
	}
	
	private DataSource getDataSource() throws NamingException {
		Context context = new InitialContext();
		
		DataSource theDataSource = (DataSource) context.lookup(jndiName);
		
		return theDataSource;
	}
	
	public List<Tarefa> getTarefas() throws Exception {
		
		List<Tarefa> tarefas = new ArrayList<>();
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			
			myConn = getConnection();
			String sql = "select * from tarefa order by id";
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);
			
			while (myRs.next()) {
				
				int id = myRs.getInt("id");
				String titulo = myRs.getString("titulo");
				String descricao = myRs.getString("descricao");
				String responsavel = myRs.getString("responsavel");
				String prioridade = myRs.getString("prioridade");
				String deadline = myRs.getString("deadline");
				String situacao = myRs.getString("situacao");
				
				Tarefa tempTarefa = new Tarefa(id, titulo, descricao, responsavel, prioridade, deadline, situacao);
				
				tarefas.add(tempTarefa);
			}
			
			return tarefas;
		}
		
		finally {
			close (myConn,myStmt,myRs);
		}
	}
	
	public void addTarefa(Tarefa theTarefa) throws Exception {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			myConn = getConnection();
			
			String sql = "insert into tarefa (titulo, descricao, responsavel, prioridade, deadline, situacao) values (?, ?, ?, ?, ?, ?)";
			
			myStmt = myConn.prepareStatement(sql);
			
			myStmt.setString(1, theTarefa.getTitulo());
			myStmt.setString(2, theTarefa.getDescricao());
			myStmt.setString(3, theTarefa.getResponsavel());
			myStmt.setString(4, theTarefa.getPrioridade());
			myStmt.setString(5, theTarefa.getDeadline());
			myStmt.setString(6, theTarefa.getSituacao());
			
			myStmt.execute();
		}
		
		finally {
			close (myConn, myStmt);
		}
	}
		
	public Tarefa getTarefa(int tarefaId) throws Exception {
			
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
			
		try {
			myConn = getConnection();
			
			String sql = "select * from tarefa where id=?";
			
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, tarefaId);
			myRs = myStmt.executeQuery();
				
			Tarefa theTarefa = null;
				
			if (myRs.next()) {
				int id = myRs.getInt("id");
				String titulo = myRs.getString("titulo");
				String descricao = myRs.getString("descricao");
				String responsavel = myRs.getString("responsavel");
				String prioridade = myRs.getString("prioridade");
				String deadline = myRs.getString("deadline");
				String situacao = myRs.getString("situacao");
					
				theTarefa = new Tarefa(id, titulo, descricao, responsavel, prioridade, deadline,situacao);
			}
				
			else {
				throw new Exception("Could not find tarefa id: " + tarefaId);
			}
				
			return theTarefa;
		}
			
		finally {
			close (myConn, myStmt, myRs);
		}
	}
		
	public void updateTarefa(Tarefa theTarefa) throws Exception {
			
		Connection myConn = null;
		PreparedStatement myStmt = null;
			
		try {
			myConn = getConnection();
				
			String sql = "update tarefa "
					+ " set titulo=?, descricao=?, responsavel=?, prioridade=?, deadline=?, situacao=?"
					+ " where id=?";
				
			myStmt = myConn.prepareStatement(sql);
				
			myStmt.setString(1, theTarefa.getTitulo());
			myStmt.setString(2, theTarefa.getDescricao());
			myStmt.setString(3, theTarefa.getResponsavel());
			myStmt.setString(4, theTarefa.getPrioridade());
			myStmt.setString(5, theTarefa.getDeadline());
			myStmt.setString(6, theTarefa.getSituacao());
			myStmt.setInt(7, theTarefa.getId());
				
			myStmt.execute();
		}
			
		finally {
			close (myConn, myStmt);
		}
	}
			
	public void deleteTarefa(int tarefaId) throws Exception {
			
		Connection myConn = null;
		PreparedStatement myStmt = null;
			
		try {
			myConn = getConnection();

			String sql = "delete from tarefa where id=?";
				
			myStmt = myConn.prepareStatement(sql);
				
			myStmt.setInt(1, tarefaId);
				
			myStmt.execute();
		}
			
		finally {
			close (myConn, myStmt);
		}
	}
		
	public void concluirTarefa(Tarefa theTarefa) throws Exception {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
			
		try {
			myConn = getConnection();

			String sql = "update tarefa " + " set situacao=?" + " where id=?";
			myStmt = myConn.prepareStatement(sql);
			
			myStmt.setInt(2, theTarefa.getId());
			myStmt.setString(1, "Concluida");
			myStmt.execute();
		}
			
		finally {
			close (myConn, myStmt);
		}
	}
	
	public List<Tarefa> buscarTarefas(int idBusca, String tituloBusca, String descricaoBusca,String responsavelBusca, String situacaoBusca) throws Exception {
		
		List<Tarefa> tarefas = new ArrayList<>();
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		Integer i = idBusca;
				
		try {
			
			myConn = getConnection();
			String sql = "select * from tarefa where ";
			
			//if(tituloBusca!=null) {
				sql = sql + " titulo like '%"+tituloBusca+"%' ";
			//i.toString()!=null || }
			if(idBusca!=0) {
				sql = sql + " and id="+idBusca+" ";
			}
			if(descricaoBusca!=null) {
				sql = sql + " and descricao like '%"+descricaoBusca+"%' ";
			}
			if(responsavelBusca!=null) {
				sql = sql + " and responsavel like '%"+responsavelBusca+"%' ";
			}
			if(situacaoBusca!=null) {
				sql = sql + " and situacao like '%"+situacaoBusca+"%' ";
			}
			
			System.out.println(sql);
	
			 
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);
			
			while (myRs.next()) {
				
				int id = myRs.getInt("id");
				String titulo = myRs.getString("titulo");
				String descricao = myRs.getString("descricao");
				String responsavel = myRs.getString("responsavel");
				String prioridade = myRs.getString("prioridade");
				String deadline = myRs.getString("deadline");
				String situacao = myRs.getString("situacao");
				
				Tarefa tempTarefa = new Tarefa(id, titulo, descricao, responsavel, prioridade, deadline, situacao);
				
				tarefas.add(tempTarefa);
			}
			
			return tarefas;
		}
		
		finally {
			close (myConn,myStmt,myRs);
		}
		
	}
		
		/*--------------------------------------------
		String sqlTitulo = tituloE;
		String sqlComando = "";
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		System.out.println("Select * from sssss where id=?");
		*/
	
	private Connection getConnection() throws Exception {

		Connection theConn = dataSource.getConnection();
			
		return theConn;
	}
	
	private void close(Connection theConn, Statement theStmt) {
		close(theConn, theStmt, null);
	}
	
	private void close(Connection theConn, Statement theStmt, ResultSet theRs) {

		try {
			if (theRs != null) {
				theRs.close();
			}

			if (theStmt != null) {
				theStmt.close();
			}

			if (theConn != null) {
				theConn.close();
			}
			
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
}
