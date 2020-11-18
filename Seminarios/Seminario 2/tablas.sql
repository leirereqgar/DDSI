CREATE TABLE Stock{
	Cproducto INT NOT NULL,
	Cantidad INT,
	PRIMARY KEY (Cproducto)
};

CREATE TABLE Pedido{
	Cpedido INT NOT NULL,
	Ccliente INT,
	Fecha-pedido DATE,
	PRIMARY KEY (Cpedido)
};

CREATE TABLE Detalle-pedido{
	Cpedido INT NOT NULL FOREIGN KEY REFERENCES Pedido(Cpedido),
	Cproducto INT NOT NULL FOREIGN KEY REFERENCES Producto(Cproducto),
	Cantidad INT,
	PRIMARY KEY (Cpedido, Cproducto),
};
