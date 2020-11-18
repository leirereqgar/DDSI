CREATE TABLE Stock(
	Cproducto INT NOT NULL,
	Cantidad INT,
	PRIMARY KEY (Cproducto)
);

CREATE TABLE Pedido(
	Cpedido INT NOT NULL,
	Ccliente INT,
	Fecha_pedido DATE,
	PRIMARY KEY (Cpedido)
);

CREATE TABLE Detalle_pedido(
  Cpedido INT NOT NULL,
  Cproducto INT NOT NULL,
  Cantidad int,
  FOREIGN KEY(Cpedido) REFERENCES Pedido(Cpedido),
  FOREIGN KEY(Cproducto) REFERENCES Producto(Cproducto),
  PRIMARY KEY(Cpedido,Cproducto)
);