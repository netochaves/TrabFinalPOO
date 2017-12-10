<%@page import="model.Estabelecimento"%>
<%@page import="DAO.EstabelecimentoDAO"%>
<%@page import="DAO.CompraDAO"%>
<%@page import="model.Compra"%>
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
                <li><a href="Titular.jsp">Titulares</a></li>
                <li><a href="Dependente.jsp">Dependentes</a></li>
              </ul>
            </li>
            <li> <a href="Compra.jsp"> <i class="icon-grid"></i>Compras </a></li>
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
              <li class="breadcrumb-item active">Compras</li>
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
                      <h3 class="h4">Registrar Compra</h3>
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
                      <form method="post" action="Compra">
                        <div class="form-group">
                          <label class="form-control-label">Cartão</label>
                          <input type="hidden" name="action" value="inserir">                         
                          <input type="text" class="form-control" name="cartao" required>
                        </div>
                        <div class="form-group">       
                          <label class="form-control-label">Estabelecimento</label>
                          <input type="text" class="form-control" name="estabelecimento" required>
                        </div>
                        <div class="form-group">       
                          <label class="form-control-label">Valor</label>
                          <input type="text"class="form-control" name="valor" required>
                        </div>
                        <div class="form-group">       
                          <label class="form-control-label">Parcelas</label>
                          <input type="text"class="form-control" name="parcelas" required>
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
                      <h3 class="h4">Compras</h3>
                    </div>
                    <div class="card-body">
                      <table class="table table-bordered table-hover">
                        <thead>
                          <tr>
                            <th>Cartão</th>
                            <th>Data</th>
                            <th>Estabelecimento</th>
                            <th>Valor</th>
                          </tr>
                        </thead>
                       <% ArrayList<Compra> compras = (ArrayList<Compra>)request.getAttribute("compras");%>
                          <%int i=0; %>
                          	<% if(compras==null){compras = CompraDAO.getAll();} %>
                          	<% for(Compra c : compras){ %> 
                        <tbody>                              	
                          	<tr data-toggle="collapse" data-target="#myCollpase<%=i%>" aria-expandede="false" aria-controls="myCollpase<%=i%>">
                          		<td><%=c.getCartao() %></td>
                          		<%int mes= c.getData().getMonth()+1;%>
                          		<%int ano= c.getData().getYear()+1900; %>
                          		<td><%=c.getData().getDate()+"/"+mes+"/"+ano %></td>
                          		<%Estabelecimento e = EstabelecimentoDAO.pesquisar(c.getEstabelecimento()); %>
                          		<td><%=e.getNome()%></td>
                          		<td><%=c.getValor()%></td>                             
                          	</tr>                         	
                        </tbody>
                        <%i++; } %>
                      </table>                         
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
  </body>
</html>