# Installation

Let UltiSnips know where java.snippets is by adding this to your $MYVIMRC file:

    set runtimepath+=~/spring

Replace ~/spring with the directory where you cloned this repository.


# Snippets

 - col - creates a field, a setter method, and a getter method for a column.
 - rel - creates a field, a setter method, and a getter method for a relation.

# Examples

## rel

    private Customer customer;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "CUSTOMER_ID_FK"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    public void setCustomer(final Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

## col

    private String emailAddress;

    @Column(name = "email", nullable = true, unique=false)
    public void setEmailAddress(final String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

