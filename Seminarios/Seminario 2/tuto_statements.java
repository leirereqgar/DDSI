// Prepare to insert new names in the EMP table
PreparedStatement pstmt = null;
try{
    pstmt = conn.prepareStatement ("insert into EMP (EMPNO, ENAME) values (?, ?)");

    // Add LESLIE as employee number 1500
    pstmt.setInt (1, 1500);          // The first ? is for EMPNO
    pstmt.setString (2, "LESLIE");   // The second ? is for ENAME
    // Do the insertion
    pstmt.execute ();

    // Add MARSHA as employee number 507
    pstmt.setInt (1, 507);           // The first ? is for EMPNO
    pstmt.setString (2, "MARSHA");   // The second ? is for ENAME
    // Do the insertion
    pstmt.execute ();
}

finally{
                if(pstmt!=null)

    // Close the statement
    pstmt.close();
}
