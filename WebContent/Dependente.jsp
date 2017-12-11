<%@page import="model.Dependente"%>
<%@page import="DAO.ClientesDAO"%>
<%@page import="model.Cliente"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
  <style>
  tr.collapse.in {
  display:table-row;
	}
  </style>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Administradora de Cartões</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="robots" content="all,follow">
    <!-- Bootstrap CSS-->
    <link rel="stylesheet" href="vendor/bootstrap/css/bootstrap.min.css">
    <!-- Fontastic Custom icon font-->
    <link rel="stylesheet" href="css/fontastic.css">
    <!-- Font Awesome CSS-->
    <link rel="stylesheet" href="vendor/font-awesome/css/font-awesome.min.css">
    <!-- Google fonts - Poppins -->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Poppins:300,400,700">
    <!-- theme stylesheet-->
    <link rel="stylesheet" href="css/style.default.css" id="theme-stylesheet">
    <!-- Custom stylesheet - for your changes-->
    <link rel="stylesheet" href="css/custom.css">
    <!-- Favicon-->
    <link rel="shortcut icon" href="favicon.png">
    <!-- Tweaks for older IEs--><!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script><![endif]-->
  </head>
  <body>
    <div class="page form-page">
      <!-- Main Navbar-->
      <header class="header">
        <nav class="navbar">
          <!-- Search Box-->
          <div class="search-box">
            <button class="dismiss"><i class="icon-close"></i></button>
            <form id="searchForm" action="Titular" role="search" method="post">
              <input type="hidden" name="action" value="pesquisar">
              <input type="search" placeholder="O que você está procurando?..." class="form-control" name="pesquisa">   
              <input type="submit" style="visibility: hidden;" />           
            </form>
          </div>
          <div class="container-fluid">
            <div class="navbar-holder d-flex align-items-center justify-content-between">
              <!-- Navbar Header-->
              <div class="navbar-header">
                <!-- Navbar Brand --><a href="index.html" class="navbar-brand">
                  <div class="brand-text brand-big"><span>Administrador</span></div>
                  <div class="brand-text brand-small"><strong>ADM</strong></div></a>
                <!-- Toggle Button--><a id="toggle-btn" href="#" class="menu-btn active"><span></span><span></span><span></span></a>
              </div>
              <!-- Navbar Menu -->
              <ul class="nav-menu list-unstyled d-flex flex-md-row align-items-md-center">
                <!-- Search-->
                <li class="nav-item d-flex align-items-center"><a id="search" href="#"><i class="icon-search"></i></a></li>
              </ul>
            </div>
          </div>
        </nav>
      </header>
      <div class="page-content d-flex align-items-stretch"> 
        <!-- Side Navbar -->
        <nav class="side-navbar">
          <!-- Sidebar Header-->
          <div class="sidebar-header d-flex align-items-center">
            <div class="avatar"><img src="img/avatar.png" alt="..." class="img-fluid rounded-circle"></div>
            <div class="title">
              <h1 class="h4">Neto Chaves</h1>
              <p>Administrador</p>
            </div>
          </div>
          <!-- Sidebar Navidation Menus--><span class="heading">Main</span>
          <ul class="list-unstyled">
            <li> <a href="index.html"><i class="icon-home"></i>Home</a></li>
            <li><a href="#dashvariants" aria-expanded="false" data-toggle="collapse"> <i class="icon-interface-windows"></i>Clientes </a>
              <ul id="dashvariants" class="collapse list-unstyled">
                <li><a href="Titular">Titulares</a></li>
                <li><a href="Dependente">Dependentes</a></li>
              </ul>
            </li>
            <li> <a href="Compra"> <i class="icon-grid"></i>Compras </a></li>
            <li> <a href="Estabelecimento"> <i class="fa fa-bar-chart"></i>Estabelecimentos </a></li>
            <li class="active"><a href="forms.html"> <i class="icon-padnote"></i>Pagamentos</a></li>
          </ul>
        </nav>
        <div class="content-inner">
          <!-- Page Header-->
          <header class="page-header">
            <div class="container-fluid">
              <h2 class="no-margin-bottom">Forms</h2>
            </div>
          </header>
          <!-- Breadcrumb-->
          <div class="breadcrumb-holder container-fluid">
            <ul class="breadcrumb">
              <li class="breadcrumb-item"><a href="index.html">Home</a></li>
              <li class="breadcrumb-item active">Forms</li>
            </ul>
          </div>
          <!-- Forms Section-->
          <section class="forms"> 
            <div class="container-fluid">
              <div class="row">
                <!-- Basic Form-->
                <div class="col-lg-5">
                  <div class="card">
                    <div class="card-header d-flex align-items-center">
                      <h3 class="h4">Cadastrar Dependente</h3>
                    </div>
                    <div class="card-body">
                    <%ArrayList<String> messages = (ArrayList<String>)request.getAttribute("messages"); %>
                    <%if(messages!=null){ %>
                    	<%for(String s : messages){ %>
                           <div class="alert alert-danger" role="alert">
  								<strong>Erro!</strong><%=" " + s %>
						   </div>
						 <% } %>
                    <%}%>
                      <form method="post" action="Dependente">
                        <div class="form-group">
                          <label class="form-control-label">CPF</label>
                          <input type="hidden" name="action" value="inserir">                         
                          <input type="text" class="form-control" name="cpf" required>
                        </div>
                        <div class="form-group">       
                          <label class="form-control-label">Nome</label>
                          <input type="text" class="form-control" name="nome" required>
                        </div>
                        <div class="form-group">       
                          <label class="form-control-label">Limite</label>
                          <input type="text"class="form-control" name="limite" required>
                        </div>
                        <div class="form-group">       
                          <label class="form-control-label">CPF do Titular</label>
                          <input type="text"class="form-control" name="cpfTitular" required>
                        </div>
                        <div class="form-group">       
                          <label class="form-control-label">Endereço</label>
                          <input type="text" class="form-control" name="endereco" required>
                        </div>
                        <div class="form-group">       
                          <label class="form-control-label">Cidade</label>
                          <input type="text" class="form-control" name="cidade" required>
                        </div>
                        <div class="form-group">
                          <label class="form-control-label">Estado</label>
                            <select name="estado" class="form-control">
                              <option>Selecione</option>
                              <option>AC</option>
                              <option>AL</option>
                              <option>AP</option>
                              <option>AM</option>
                              <option>BA</option>
                              <option>CE</option>
                              <option>DF</option>
                              <option>ES</option>
                              <option>GO</option>
                              <option>MA</option>
                              <option>MT</option>
                              <option>MS</option>
                              <option>MG</option>
                              <option>PA</option>
                              <option>PB</option>
                              <option>PR</option>
                              <option>PE</option>
                              <option>PI</option>
                              <option>RJ</option>
                              <option>RN</option>
                              <option>RS</option>
                              <option>RO</option>
                              <option>RR</option>
                              <option>SC</option>
                              <option>SP</option>
                              <option>SE</option>
                              <option>TO</option>
                            </select>
                        </div>
                        <div class="form-group">       
                          <input type="submit" value="Cadastrar" class="btn btn-primary">
                        </div>
                      </form>
                    </div>
                  </div>
                </div>
                <div class="col-lg-7" style="float:right">
                  <div class="card">
                    <div class="card-close">
                    </div>
                    <div class="card-header d-flex align-items-center">
                      <h3 class="h4">Dependente</h3>
                    </div>
                    <div class="card-body">
                      <table class="table table-bordered table-hover">
                        <thead>
                          <tr>
                            <th>CPF</th>
                            <th>Nome</th>
                            <th>Cartão</th>
                            <th>Limite</th>
                            <th>Ação</th>
                          </tr>
                        </thead>
                       <% ArrayList<Dependente> dependentes = (ArrayList<Dependente>)request.getAttribute("dependentes");%>
                          <%int i=0; %>
                          	<% if(dependentes==null){dependentes = ClientesDAO.getAllDependentes();} %>
                          	<% for(Dependente c : dependentes){ %> 
                        <tbody>                              	
                          	<tr data-toggle="collapse" data-target="#myCollpase<%=i%>" aria-expandede="false" aria-controls="myCollpase<%=i%>">
                          		<td><%=c.getCpf() %></td>
                          		<td><%=c.getNome() %></td>
                          		<td><%=c.getNumCartao() %></td>
                          		<td><%=c.getLimite() %></td>
                          		<td>
                          		<div class="input-group">
                          		<div class="input-group-btn">
                                <button data-toggle="dropdown" type="button" class="btn btn-white dropdown-toggle">Ação<span class="caret"></span></button>
                                <ul class="dropdown-menu">
                                  <li><a href="Dependente?action=remover&cpf=<%=c.getCpf()%>">Remover</a></li>
					              <li><a data-toggle="modal" data-target="#meuModal" data-cpf="<%=c.getCpf()%>" data-nome="<%=c.getNome() %>" data-endereco="<%=c.getEndereco() %>" data-cidade="<%=c.getCidade() %>" data-estado="<%=c.getEstado() %>" data-titular="<%=c.getTitularCpf()%>">Editar</a></li>
                                </ul>
                                </div>
                                </div>
								</td>                              
                          	</tr>                         	
                        </tbody>
                        <tbody class="collapse" id="myCollpase<%=i%>">
                        	<tr> 
                        		<td>Titular</td>
                        		<%Cliente t = ClientesDAO.pesquisar(c.getTitularCpf()); %>                         		
                          		<td><%=t.getNome() %></td>
                          	</tr>
                        	<tr> 
                        		<td>Endereço</td>                         		
                          		<td><%=c.getEndereco() %></td>
                          	</tr>
                          	<tr>
                          	    <td>Cidade</td>
                          		<td><%=c.getCidade() %></td>
                          	</tr>
                          	<tr>
                          	    <td>Estado</td>
                          		<td><%=c.getEstado() %></td>
                          	</tr>
                        </tbody>
                        <%i++; } %>
                      </table>
								<!-- Modal-->
					<div id="meuModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true" class="modal fade">
					              <div role="document" class="modal-dialog">
					                    <div class="modal-content">
					                            <div class="modal-header">
					                              <h4 id="exampleModalLabel" class="modal-title">Alterar Dependente</h4>
					                              <button type="button" data-dismiss="modal" aria-label="Close" class="close"><span aria-hidden="true">×</span></button>
					                            </div>
					                            <div class="modal-body"> 
					                              <form action="Dependente" method="post" id="form1">
					                              <div class="form-group">
					                                  <label>CPF</label>
					                                  <input type="hidden" name="action" value="alterar">
					                                  <input type="text" class="form-control" id="cpf" name="cpf" readonly="readonly" required>
					                               </div>
					                                <div class="form-group">
					                                  <label>CPF do Titular</label>
					                                  <input type="text" class="form-control" id="cpfTitular" name="cpfTitular" readonly="readonly" required>
					                               </div>
					                                <div class="form-group">
					                                  <label>Nome</label>
					                                  <input type="text" class="form-control" id="nome" name="nome" required>
					                                </div>
					                                <div class="form-group">       
					                                  <label>Endereco</label>
					                                  <input type="text" class="form-control" id="endereco" name="endereco" required>
					                                </div>
					                                <div class="form-group">       
					                                  <label>Cidade</label>
					                                  <input type="text" class="form-control" id="cidade" name="cidade" required>
					                                </div>
					                                <div class="form-group">       
					                                  <label>Estado</label>
					                                  <select name="estado" class="form-control" id="estado" name="estado">
						                              <option>Selecione</option>
						                              <option>AC</option>
						                              <option>AL</option>
						                              <option>AP</option>
						                              <option>AM</option>
						                              <option>BA</option>
						                              <option>CE</option>
						                              <option>DF</option>
						                              <option>ES</option>
						                              <option>GO</option>
						                              <option>MA</option>
						                              <option>MT</option>
						                              <option>MS</option>
						                              <option>MG</option>
						                              <option>PA</option>
						                              <option>PB</option>
						                              <option>PR</option>
						                              <option>PE</option>
						                              <option>PI</option>
						                              <option>RJ</option>
						                              <option>RN</option>
						                              <option>RS</option>
						                              <option>RO</option>
						                              <option>RR</option>
						                              <option>SC</option>
						                              <option>SP</option>
						                              <option>SE</option>
						                              <option>TO</option>
						            </select>
					              </div>
					            </form>
					            </div>
					           <div class="modal-footer">
					           <button type="submit" form="form1" class="btn btn-primary">Salvar</button>
					         </div>
					       </div>
					     </div>
					   </div>                             
                    </div>
                  </div>
                </div>   
              </div>
            </div>
          </section>
          <!-- Page Footer-->
          <footer class="main-footer">
            <div class="container-fluid">
              <div class="row">
                <div class="col-sm-6">
                  <p>UFPI&copy; 2017</p>
                </div>
              </div>
            </div>
          </footer>
        </div>
      </div>
    </div>
    <!-- Javascript files-->

    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <script src="vendor/popper.js/umd/popper.min.js"> </script>
    <script src="vendor/bootstrap/js/bootstrap.min.js"></script>
    <script src="vendor/jquery.cookie/jquery.cookie.js"> </script>
    <script src="vendor/jquery-validation/jquery.validate.min.js"></script>
    <script src="js/front.js"></script>
        <script type="text/javascript">
        $('#meuModal').on('show.bs.modal', function (event) {
        	  var button = $(event.relatedTarget) 
        	  var nome = button.data('nome')
        	  var cpf = button.data('cpf')
        	  var endereco = button.data('endereco')
        	  var cidade = button.data('cidade')
        	  var estado = button.data('estado')
        	  var cpfTitular = button.data('titular')
        	  
        	  var modal = $(this)
        	  modal.find('.modal-title').text('New message to ' + nome	)
        	  modal.find('#nome').val(nome)
        	  modal.find('#cpf').val(cpf)
        	  modal.find('#endereco').val(endereco)
        	  modal.find('#cidade').val(cidade)
        	  modal.find('#estado').val(estado)
        	  modal.find('#cpfTitular').val(cpfTitular)
        	}) 
 	</script>
  </body>
</html>