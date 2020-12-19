AQADEMIA = $${HOME}/texmf/tex/latex/aqademia/

all: aqademia build

aqademia:
	@  (find $(AQADEMIA) \
	       && git -C $(AQADEMIA) pull) \
	    || (mkdir -p ~/texmf/tex/latex \
	       && git clone https://github.com/Groctel/aqademia $(AQADEMIA))
       
build:
	xelatex seminario3.tex
