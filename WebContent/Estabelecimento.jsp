<%@page import="DAO.EstabelecimentoDAO"%>
<%@page import="model.Estabelecimento"%>
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
            <form id="searchForm" action="Estabelecimento" role="search" method="post">
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
            <li> <a href="charts.html"> <i class="fa fa-bar-chart"></i>Faturas </a></li>
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
                <div class="col-lg-4">
                  <div class="card">
                    <div class="card-header d-flex align-items-center">
                      <h3 class="h4">Cadastrar Estabelecimento</h3>
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
                      <form method="post" action="Estabelecimento">
                        <div class="form-group">
                          <label class="form-control-label">CNPJ</label>
                          <input type="hidden" name="action" value="inserir">                         
                          <input type="text" class="form-control" name="cnpj" required>
                        </div>
                        <div class="form-group">       
                          <label class="form-control-label">Nome</label>
                          <input type="text" class="form-control" name="nome" required>
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
                          <label class="form-control-label">Email</label>
                          <input type="text" class="form-control" name="email" required>
                        </div>
                        
                        <div class="form-group">       
                          <label class="form-control-label">Telefone</label>
                          <input type="text" class="form-control" name="telefone" required>
                        </div>
                        
                        <div class="form-group">       
                          <label class="form-control-label">Parcelas</label>
                          <input type="text" class="form-control" name="parcelas" required>
                        </div>
                        
                        <div class="form-group">       
                          <input type="submit" value="Cadastrar" class="btn btn-primary">
                        </div>
                      </form>
                    </div>
                  </div>
                </div>
                <div class="col-lg-8" style="float:right">
                  <div class="card">
                    <div class="card-close">
                    </div>
                    <div class="card-header d-flex align-items-center">
                      <h3 class="h4">Estabelecimentos</h3>
                    </div>
                    <div class="card-body">
                      <table class="table table-bordered table-hover">
                        <thead>
                          <tr>
                            <th>CNPJ</th>
                            <th>Nome</th>
                            <th>Endereço</th>
                            <th>Email</th>
                            <th>Ação</th>
                          </tr>
                        </thead>
                       <% ArrayList<Estabelecimento> estabelecimentos = (ArrayList<Estabelecimento>)request.getAttribute("estabelecimentos");%>
                          <%int i=0; %>
                          	<% if(estabelecimentos==null){estabelecimentos = EstabelecimentoDAO.getAll();} %>
                          	<% for(Estabelecimento c : estabelecimentos){ %> 
                        <tbody>                              	
                          	<tr data-toggle="collapse" data-target="#myCollpase<%=i%>" aria-expandede="false" aria-controls="myCollpase<%=i%>">
                          		<td><%=c.getCnpj() %></td>
                          		<td><%=c.getNome() %></td>
                          		<td><%=c.getEndereco() %></td>
                          		<td><%=c.getEmail() %></td>
                          		<td>
                          		<div class="input-group">
                          		<div class="input-group-btn">
                                <button data-toggle="dropdown" type="button" class="btn btn-white dropdown-toggle">Ação<span class="caret"></span></button>
                                <ul class="dropdown-menu">
                                  <li><a href="Estabelecimento?action=remover&cnpj=<%=c.getCnpj()%>">Remover</a></li>
					              <li><a data-toggle="modal" data-target="#meuModal" data-cnpj="<%=c.getCnpj()%>" data-nome="<%=c.getNome() %>" data-endereco="<%=c.getEndereco() %>" data-cidade="<%=c.getCidade() %>" data-estado="<%=c.getEstado() %>" data-email="<%=c.getEmail()%>" data-telefone="<%=c.getTelefone() %>" data-parcelas="<%=c.getParcelas()%>">Editar</a></li>
                                </ul>
                                </div>
                                </div>
								</td>                              
                          	</tr>                         	
                        </tbody>
                        <tbody class="collapse" id="myCollpase<%=i%>">
                        	<tr> 
                        		<td>Telefone</td>                      		
                          		<td><%=c.getTelefone() %></td>
                          	</tr>
                        	<tr> 
                        		<td>Cidade</td>                         		
                          		<td><%=c.getCidade() %></td>
                          	</tr>
                          	<tr>
                          	    <td>Estado</td>
                          		<td><%=c.getEstado() %></td>
                          	</tr>
                          	<tr>
                          	    <td>Parcelas</td>
                          		<td><%=c.getParcelas() %></td>
                          	</tr>
                        </tbody>
                        <%i++; } %>
                      </table>
								<!-- Modal-->
					<div id="meuModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true" class="modal fade">
					              <div role="document" class="modal-dialog">
					                    <div class="modal-content">
					                            <div class="modal-header">
					                              <h4 id="exampleModalLabel" class="modal-title">Alterar Estabelecimento</h4>
					                              <button type="button" data-dismiss="modal" aria-label="Close" class="close"><span aria-hidden="true">×</span></button>
					                            </div>
					                            <div class="modal-body"> 
					                              <form action="Estabelecimento" method="post" id="form1">
					                              <div class="form-group">
					                                  <label>CNPJ</label>
					                                  <input type="hidden" name="action" value="alterar">
					                                  <input type="text" class="form-control" id="cnpj" name="cnpj" readonly="readonly" required>
					                               </div>
					                                <div class="form-group">
					                                  <label>Nome</label>
					                                  <input type="text" class="form-control" id="nome" name="nome" required>
					                                </div>
					                                <div class="form-group">
					                                  <label>Parcelas</label>
					                                  <input type="text" class="form-control" id="parcelas" name="parcelas" required>
					                                </div>
					                                <div class="form-group">       
					                                  <label>Endereco</label>
					                                  <input type="text" class="form-control" id="endereco" name="endereco" required>
					                                </div>
					                                <div class="form-group">       
					                                  <label>Email</label>
					                                  <input type="text" class="form-control" id="email" name="email" required>
					                                </div>
					                                <div class="form-group">       
					                                  <label>Telefone</label>
					                                  <input type="text" class="form-control" id="telefone" name="telefone" required>
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
        	  var cnpj = button.data('cnpj')
        	  var endereco = button.data('endereco')
        	  var cidade = button.data('cidade')
        	  var estado = button.data('estado')
        	  var email = button.data('email')
        	  var telefone = button.data('telefone')
        	  var parcelas = button.data('parcelas')
        	  
        	  var modal = $(this)
        	  modal.find('#nome').val(nome)
        	  modal.find('#cnpj').val(cnpj)
        	  modal.find('#endereco').val(endereco)
        	  modal.find('#cidade').val(cidade)
        	  modal.find('#estado').val(estado)
        	  modal.find('#email').val(email)
        	  modal.find('#telefone').val(telefone)
        	  modal.find('#parcelas').val(parcelas)
        	}) 
 	</script>
 	<script type="text/javascript">
 	$(document).ready(function() {
 		$('[data-toggle="toggle"]').change(function(){
 			$(this).parents().next('.hide').toggle();
 		});
 	});
 	</script>

  </body>
</html>