<%@page import="controle.AdministradoraCartaoDeCredito"%>
<%@page import="DAO.FaturaDAO"%>
<%@page import="model.Fatura"%>
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
    <!-- Date Picker CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.7.1/css/bootstrap-datepicker.css" />
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
            <form id="searchForm" action="Fatura" role="search" method="post">
              <input type="hidden" name="acao" value="pesquisar">
              <input type="search" placeholder="Digite um número de cartão" class="form-control" name="pesquisa">   
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
            <li><a href="#dashvariants" aria-expanded="false" data-toggle="collapse"> <i class="icon icon-user"></i>Clientes </a>
              <ul id="dashvariants" class="collapse list-unstyled">
                <li><a href="Titular.jsp">Titulares</a></li>
                <li><a href="Dependente.jsp">Dependentes</a></li>
              </ul>
            </li>
            <li> <a href="Estabelecimento"> <i class="icon icon-home"></i>Estabelecimentos </a></li>
            <li> <a href="Compra"> <i class="icon icon-check"></i>Compras </a></li>
            <li class="active"><a href="Fatura.jsp"> <i class="icon icon-bill"></i>Faturas</a></li>            
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
                <div class="col-lg-8" style="float:right">
                  <div class="card">
                    <div class="card-header d-flex align-items-right">
                      <h3 class="h4">Faturas</h3>
                    </div>
                    <button type="button" data-toggle="modal" data-target="#myModal" class="btn btn-primary">Faturar</button>  	                                                
                    <div class="card-body">
                    <%ArrayList<String> messages = (ArrayList<String>)request.getAttribute("messages"); %>
                    <%if(messages!=null){ %>
                    	<%for(String s : messages){ %>
                           <div class="alert alert-danger" role="alert">
  								<strong>Erro!</strong><%=" " + s %>
						   </div>
						 <% } %>
                    <%}%>
                      <table class="table table-bordered table-hover">
                        <thead>
                          <tr>
                            <th>Cartão</th>
                            <th>Data</th>
                            <th>Compras</th>
                            <th>Juros</th>
                            <th>Pagamentos</th>
                          </tr>
                        </thead>
                       <% ArrayList<Fatura> faturas = (ArrayList<Fatura>)request.getAttribute("faturas");%>
                          	<% if(faturas==null){faturas = FaturaDAO.getAll();} %>
                          	<% for(Fatura c : faturas){ %> 
                        <tbody>                              	
                          	<tr>
                          		<td><%=c.getCartao() %></td>
                          		<td><%=c.getMes()+"/"+c.getAno() %></td>
                          		<td><%=c.valorDeCompras(c.getCartao(), c.getMes(), c.getAno()) %></td>
                          		<%
                          			AdministradoraCartaoDeCredito adm = new AdministradoraCartaoDeCredito();
                          			int mes=c.getMes();
                          			int ano=c.getAno();
                          			if(mes==1){
                          				ano-=1;
                          				mes=12;
                          			}else{
                              			mes-=1;
                          			}
                          			System.out.println(mes);
                          			System.out.println(ano);
                          		%>
                          		<td><%=adm.calcularResiduo(c.getCartao(), mes, ano)%></td> 
                          		<td><a href="Pagamento?cartao=<%=c.getCartao()%>&mes=<%=c.getMes()%>&ano=<%=c.getAno()%>"><button type="button" class="btn btn-white">Pagamentos<span class="caret"></span></button></a>                     		                           
                          	</tr>                         	
                        </tbody>
                        <%}%>
                      </table>                            
                    </div>
                  </div>
                </div>   
              </div>
            </div>
          </section>
          			<!-- Modal-->
						<div id="myModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true" class="modal fade">
					       <div role="document" class="modal-dialog">
					         <div class="modal-content">
					           <div class="modal-header">
					           	<h4 id="exampleModalLabel" class="modal-title">Escolha um data</h4>
					           	<button type="button" data-dismiss="modal" aria-label="Close" class="close"><span aria-hidden="true">×</span></button>
					           </div>
					            <div class="modal-body"> 
					            	<form action="Fatura" method="post" id="form1">
					                    <div class="form-group">
						                  <div class="input-group date" id="datepicker">
										    <input type="text" class="form-control" readonly="readonly" name="data">
										    <div class="input-group-addon">
										        <span class="glyphicon glyphicon-th"></span>
										    </div>
										  </div>
									        <input type="hidden" name="acao" value="faturar">										    									  
                						</div>          
					            	</form>
					            </div>
					           <div class="modal-footer">
					           <button type="submit" form="form1" class="btn btn-primary">Confirmar</button>
					         </div>
					       </div>
					     </div>
					   </div>   
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
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.7.1/js/bootstrap-datepicker.min.js"></script>
    <script src="js/front.js"></script>    
    <script type="text/javascript">
    $(document).ready(function () {
        $('#datepicker').datepicker({
        	format: "mm/yyyy",
            minViewMode: 1
        });
    })
	</script>    
  </body>
</html>